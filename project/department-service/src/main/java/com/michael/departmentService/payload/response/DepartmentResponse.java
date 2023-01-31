package com.michael.departmentService.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentResponse {
    private Long id;
    private String departmentName;
    private String departmentDescription;
    private String departmentCode;
}
