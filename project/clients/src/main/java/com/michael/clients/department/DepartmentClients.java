package com.michael.clients.department;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("department-service")
public interface DepartmentClients {

    @CircuitBreaker(name = "department-service", fallbackMethod = "getDefaultDepartment")
    @GetMapping("/api/v1/department/{department-code}")
    DepartmentResponse getDepartmentByCode(@PathVariable("department-code") String departmentCode);


    default DepartmentResponse getDefaultDepartment(String departmentCode, Exception exception) {
        return DepartmentResponse.builder()
                .departmentCode(departmentCode)
                .departmentDescription("Default Description")
                .departmentName("Default Name")
                .build();
    }

}
