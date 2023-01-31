package com.michael.departmentService.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentRequest {
    @NotBlank
    private String departmentName;
    @NotBlank
    private String departmentDescription;
    @NotBlank
    private String departmentCode;
}
