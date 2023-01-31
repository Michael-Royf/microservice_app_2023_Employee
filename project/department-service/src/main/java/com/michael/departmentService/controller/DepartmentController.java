package com.michael.departmentService.controller;

import com.michael.departmentService.exceptions.payload.DepartmentExistExceptional;
import com.michael.departmentService.exceptions.payload.DepartmentNotFoundException;
import com.michael.departmentService.payload.request.DepartmentRequest;
import com.michael.departmentService.payload.response.DepartmentResponse;
import com.michael.departmentService.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody DepartmentRequest departmentRequest) throws DepartmentExistExceptional {
        return new ResponseEntity<>(departmentService.createDepartment(departmentRequest), CREATED);
    }

    @GetMapping("/{department-code}")
    public ResponseEntity<DepartmentResponse> getDepartmentByCode(@PathVariable("department-code") String departmentCode) throws DepartmentNotFoundException {
        return new ResponseEntity<>(departmentService.getDepartmentByCode(departmentCode), OK);
    }


    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartments(), OK);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long departmentId) throws DepartmentNotFoundException {
        return new ResponseEntity<>(departmentService.deleteDepartment(departmentId), OK);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Long departmentId,
                                                               @Valid @RequestBody DepartmentRequest departmentRequest) throws DepartmentNotFoundException, DepartmentExistExceptional {
        return new ResponseEntity<>(departmentService.updateDepartment(departmentId, departmentRequest), OK);
    }

}
