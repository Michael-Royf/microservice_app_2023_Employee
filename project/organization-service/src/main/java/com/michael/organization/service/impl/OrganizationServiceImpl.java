package com.michael.organization.service.impl;

import com.michael.clients.organization.OrganizationResponse;
import com.michael.organization.entity.Organization;
import com.michael.organization.exception.payload.OrganizationAlreadyExists;
import com.michael.organization.exception.payload.OrganizationNotFoundException;
import com.michael.organization.payload.request.OrganizationRequest;
import com.michael.organization.repository.OrganizationRepository;
import com.michael.organization.service.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public OrganizationResponse saveOrganization(OrganizationRequest organizationRequest) throws OrganizationAlreadyExists {
        Optional<Organization> organizationDB = organizationRepository.findByOrganizationCode(organizationRequest.getOrganizationCode());
        if (organizationDB.isPresent()) {
            throw new OrganizationAlreadyExists(String.format("Organization with code already exists", organizationRequest.getOrganizationCode()));
        }

        Organization organization = Organization.builder()
                .organizationCode(organizationRequest.getOrganizationCode())
                .organizationName(organizationRequest.getOrganizationName())
                .organizationDescription(organizationRequest.getOrganizationDescription())
                .build();
        organization = organizationRepository.save(organization);
        return mapper.map(organization, OrganizationResponse.class);
    }

    @Override
    public OrganizationResponse getOrganizationByCode(String organizationCode) throws OrganizationNotFoundException {
        Organization organization = findOrganizationByCodeFromDB(organizationCode);
        return mapper.map(organization, OrganizationResponse.class);

    }

    @Override
    public List<OrganizationResponse> getAllOrganization() {
        return organizationRepository.findAll()
                .stream()
                .map(organization -> mapper.map(organization, OrganizationResponse.class))
                .collect(Collectors.toList());
    }


    private Organization findOrganizationByCodeFromDB(String organizationCode) throws OrganizationNotFoundException {
        return organizationRepository.findByOrganizationCode(organizationCode)
                .orElseThrow(() -> new OrganizationNotFoundException(String.format("Organization with code %s not found", organizationCode)));
    }
}
