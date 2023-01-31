package com.michael.employeeService.service;

import com.michael.employeeService.exceptions.payload.EmailExistException;
import com.michael.employeeService.exceptions.payload.EmployeeNotFoundException;
import com.michael.employeeService.payload.request.EmployeeRequest;
import com.michael.employeeService.payload.response.ApiResponseDto;
import com.michael.employeeService.payload.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest employeeRequest) throws EmailExistException;

    List<EmployeeResponse> getAllEmployee();

    String deleteEmployee(Long employeeId) throws EmployeeNotFoundException;

    EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest employeeRequest) throws EmployeeNotFoundException, EmailExistException;

    ApiResponseDto getEmployeeById(Long id) throws EmployeeNotFoundException;

}
