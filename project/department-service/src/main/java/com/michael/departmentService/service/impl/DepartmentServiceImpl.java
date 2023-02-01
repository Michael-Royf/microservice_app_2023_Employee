package com.michael.departmentService.service.impl;

import com.michael.clients.department.DepartmentResponse;
import com.michael.departmentService.entity.Department;
import com.michael.departmentService.exceptions.payload.DepartmentExistExceptional;
import com.michael.departmentService.exceptions.payload.DepartmentNotFoundException;
import com.michael.departmentService.payload.request.DepartmentRequest;

import com.michael.departmentService.repository.DepartmentRepository;
import com.michael.departmentService.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    public static final String DEPARTMENT_ALREADY_EXISTS = "Department code %s already exists";
    public static final String DEPARTMENT_DELETED = "Department with id %s was deleted";
    public static final String DEPARTMENT_NOT_FOUND = "Department  not found";


    @Autowired
    private ModelMapper mapper;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) throws DepartmentExistExceptional {
        Optional<Department> departmentDB = getOptionalDepartmentByDepartmentCode(departmentRequest.getDepartmentCode());
        if (departmentDB.isPresent()) {
            throw new DepartmentExistExceptional(String.format(DEPARTMENT_ALREADY_EXISTS, departmentRequest.getDepartmentCode()));
        }

        Department department = Department.builder()
                .departmentName(departmentRequest.getDepartmentName())
                .departmentDescription(departmentRequest.getDepartmentDescription())
                .departmentCode(departmentRequest.getDepartmentCode())
                .build();

        department = departmentRepository.save(department);
        return mapper.map(department, DepartmentResponse.class);
    }

    @Override
    public DepartmentResponse getDepartmentByCode(String departmentCode) throws DepartmentNotFoundException {
        Department department = getDepartmentFromDbByDepartmentCode(departmentCode);
        return mapper.map(department, DepartmentResponse.class);
    }

    @Override
    public String deleteDepartment(Long departmentId) throws DepartmentNotFoundException {
        Department department = getDepartmentByIdFromDb(departmentId);
        departmentRepository.delete(department);
        return String.format(DEPARTMENT_DELETED, departmentId);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(department -> mapper.map(department, DepartmentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest departmentRequest) throws DepartmentNotFoundException, DepartmentExistExceptional {
        Department department = getDepartmentByIdFromDb(departmentId);
        Optional<Department> departmentDB = getOptionalDepartmentByDepartmentCode(departmentRequest.getDepartmentCode());

        if (departmentDB.isPresent() && !department.getId().equals(departmentDB.get().getId())) {
            throw new DepartmentExistExceptional(String.format(DEPARTMENT_ALREADY_EXISTS, departmentRequest.getDepartmentCode()));
        }

        department.setDepartmentName(departmentRequest.getDepartmentName());
        department.setDepartmentCode(departmentRequest.getDepartmentCode());
        department.setDepartmentDescription(departmentRequest.getDepartmentDescription());
        department = departmentRepository.save(department);
        return mapper.map(department, DepartmentResponse.class);
    }


    private Department getDepartmentFromDbByDepartmentCode(String departmentCode) throws DepartmentNotFoundException {
        return departmentRepository.findByDepartmentCode(departmentCode)
                .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, departmentCode)));
    }

    private Department getDepartmentByIdFromDb(Long departmentId) throws DepartmentNotFoundException {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(String.format(DEPARTMENT_NOT_FOUND, departmentId)));
    }

    private Optional<Department> getOptionalDepartmentByDepartmentCode(String getDepartmentCode) {
        return departmentRepository.findByDepartmentCode(getDepartmentCode);
    }

}
