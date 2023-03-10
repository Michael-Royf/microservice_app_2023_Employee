package com.michael.employeeService.service.impl;

import com.michael.amqp.RabbitMQMessageProducer;
import com.michael.clients.department.DepartmentClients;
import com.michael.clients.department.DepartmentResponse;
import com.michael.clients.notification.NotificationRequest;
import com.michael.clients.organization.OrganizationClients;
import com.michael.clients.organization.OrganizationResponse;
import com.michael.employeeService.entity.Employee;
import com.michael.employeeService.exceptions.payload.EmailExistException;
import com.michael.employeeService.exceptions.payload.EmployeeNotFoundException;
import com.michael.employeeService.payload.request.EmployeeRequest;
import com.michael.employeeService.payload.response.ApiResponseDto;
import com.michael.employeeService.payload.response.EmployeeResponse;
import com.michael.employeeService.repository.EmployeeRepository;
import com.michael.employeeService.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private DepartmentClients departmentClients;
    private OrganizationClients organizationClients;
    private ModelMapper mapper;

    private RabbitMQMessageProducer rabbitMQMessageProducer;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               DepartmentClients departmentClients,
                               OrganizationClients organizationClients,
                               ModelMapper mapper,
                               RabbitMQMessageProducer rabbitMQMessageProducer) {
        this.employeeRepository = employeeRepository;
        this.departmentClients = departmentClients;
        this.organizationClients = organizationClients;
        this.mapper = mapper;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) throws EmailExistException {
        Optional<Employee> employeeDB = employeeRepository.findByEmail(employeeRequest.getEmail());
        if (employeeDB.isPresent()) {
            throw new EmailExistException(String.format("Email %s already exists", employeeRequest.getEmail()));
        }

        Employee employee = Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .departmentCode(employeeRequest.getDepartmentCode())
                .organizationCode(employeeRequest.getOrganizationCode())
                .build();
        employee = employeeRepository.save(employee);
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .employeeName(employee.getFirstName())
                .employeeEmail(employee.getEmail())
                .employeeId(employee.getId())
                .build();

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

        return mapper.map(employee, EmployeeResponse.class);
    }

    @Override
    public List<EmployeeResponse> getAllEmployee() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> mapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public String deleteEmployee(Long employeeId) throws EmployeeNotFoundException {
        Employee employee = findEmployeeByInDB(employeeId);
        employeeRepository.delete(employee);
        return String.format("Employee with id: %s was deleted", employeeId);
    }

    @Override
    public EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest employeeRequest) throws EmployeeNotFoundException, EmailExistException {
        Employee employee = findEmployeeByInDB(employeeId);
        Optional<Employee> employeeDB = employeeRepository.findByEmail(employeeRequest.getEmail());

        if (employeeDB.isPresent() && !employee.getId().equals(employeeDB.get().getId())) {
            throw new EmailExistException(String.format("Email %s already exists", employeeRequest.getEmail()));
        }

        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setDepartmentCode(employeeRequest.getDepartmentCode());
        employee = employeeRepository.save(employee);
        return mapper.map(employee, EmployeeResponse.class);
    }


    @Override
    public ApiResponseDto getEmployeeById(Long id) throws EmployeeNotFoundException {
        log.info("inside getEmployee by id");
        Employee employee = findEmployeeByInDB(id);

        DepartmentResponse departmentResponse = departmentClients.getDepartmentByCode(employee.getDepartmentCode());
        OrganizationResponse organizationResponse = organizationClients.getOrganizationByCode(employee.getOrganizationCode());

        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);

        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .employeeResponse(employeeResponse)
                .departmentResponse(departmentResponse)
                .organizationResponse(organizationResponse)
                .build();
        return apiResponseDto;
    }


    private Employee findEmployeeByInDB(Long employeeId) throws EmployeeNotFoundException {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with id: %s not found", employeeId)));
    }


}
