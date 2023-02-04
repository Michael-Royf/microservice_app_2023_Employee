package com.michael.employeeService.payload.response;

import com.michael.clients.department.DepartmentResponse;
import com.michael.clients.organization.OrganizationResponse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponseDto {
    private EmployeeResponse employeeResponse;
    private DepartmentResponse departmentResponse;
    private OrganizationResponse organizationResponse;
}
