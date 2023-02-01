package com.michael.clients.department;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("department-service")
public interface DepartmentClients {
    @GetMapping("/api/v1/department/{department-code}")
    DepartmentResponse getDepartmentByCode(@PathVariable("department-code") String departmentCode);
}
