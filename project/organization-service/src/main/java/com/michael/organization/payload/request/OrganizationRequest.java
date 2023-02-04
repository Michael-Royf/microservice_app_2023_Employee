package com.michael.organization.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrganizationRequest {
    private String organizationName;
    private String organizationDescription;
    private String organizationCode;
}
