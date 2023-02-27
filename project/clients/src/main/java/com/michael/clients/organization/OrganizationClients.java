package com.michael.clients.organization;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("organization-service")
public interface OrganizationClients {
    @CircuitBreaker(name = "organization-service", fallbackMethod = "getDefaultOrganization")
    @GetMapping("/api/v1/organizations/{organizationCode}")
    OrganizationResponse getOrganizationByCode(@PathVariable("organizationCode") String organizationCode);


    default OrganizationResponse getDefaultOrganization(String organizationCode, Exception exception) {
        return OrganizationResponse.builder()
                .organizationDescription("Default Description")
                .organizationName("Default Name")
                .organizationCode(organizationCode)
                .build();
    }
}
