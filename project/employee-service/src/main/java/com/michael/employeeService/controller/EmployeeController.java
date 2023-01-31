package com.michael.employeeService.controller;

import com.michael.employeeService.exceptions.payload.EmailExistException;
import com.michael.employeeService.exceptions.payload.EmployeeNotFoundException;
import com.michael.employeeService.payload.request.EmployeeRequest;
import com.michael.employeeService.payload.response.ApiResponseDto;
import com.michael.employeeService.payload.response.EmployeeResponse;
import com.michael.employeeService.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) throws EmailExistException {
        return new ResponseEntity<>(employeeService.createEmployee(employeeRequest), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> getEmployeeById(@PathVariable Long id) throws EmployeeNotFoundException {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), OK);
    }


    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployee() {
        return new ResponseEntity<>(employeeService.getAllEmployee(), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        return new ResponseEntity<>(employeeService.deleteEmployee(id), OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id,
                                                           @Valid @RequestBody EmployeeRequest employeeRequest) throws EmployeeNotFoundException, EmailExistException {
        return new ResponseEntity<>(employeeService.updateEmployee(id, employeeRequest), OK);
    }
}
