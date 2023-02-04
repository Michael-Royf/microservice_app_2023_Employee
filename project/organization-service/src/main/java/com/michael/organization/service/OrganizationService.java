package com.michael.organization.service;

import com.michael.clients.organization.OrganizationResponse;
import com.michael.organization.exception.payload.OrganizationAlreadyExists;
import com.michael.organization.exception.payload.OrganizationNotFoundException;
import com.michael.organization.payload.request.OrganizationRequest;


import java.util.List;

public interface OrganizationService {
    OrganizationResponse saveOrganization(OrganizationRequest organizationRequest) throws OrganizationNotFoundException, OrganizationAlreadyExists;

    OrganizationResponse getOrganizationByCode(String organizationCode) throws OrganizationNotFoundException;

    List<OrganizationResponse> getAllOrganization();
}
