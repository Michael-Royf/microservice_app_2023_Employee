package com.michael.clients.organization;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("organization-service")
public interface OrganizationClients {

    @GetMapping("/api/v1/organizations/{organizationCode}")
    OrganizationResponse getOrganizationByCode(@PathVariable("organizationCode") String organizationCode);

}
