package com.michael.employeeService.service;

import com.michael.employeeService.payload.response.DepartmentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(url = "http://localhost:8082", value = "DEPARTMENT-SERVICE")
public interface APIClient {

    @GetMapping("/api/v1/department/{department-code}")
    DepartmentResponse getDepartmentByCode(@PathVariable("department-code")String departmentCode);

}
