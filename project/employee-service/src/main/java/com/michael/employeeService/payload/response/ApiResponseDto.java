package com.michael.employeeService.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponseDto {
    private EmployeeResponse employeeResponse;
    private DepartmentResponse departmentResponse;
}
