package com.michael.departmentService.service;

import com.michael.departmentService.exceptions.payload.DepartmentExistExceptional;
import com.michael.departmentService.exceptions.payload.DepartmentNotFoundException;
import com.michael.departmentService.payload.request.DepartmentRequest;
import com.michael.departmentService.payload.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest departmentRequest) throws DepartmentExistExceptional;

    DepartmentResponse getDepartmentByCode(String departmentCode) throws DepartmentNotFoundException;

    String deleteDepartment(Long departmentId) throws DepartmentNotFoundException;

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest departmentRequest) throws DepartmentNotFoundException, DepartmentExistExceptional;

}
