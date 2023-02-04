package com.michael.organization.controller;

import com.michael.clients.organization.OrganizationResponse;
import com.michael.organization.exception.payload.OrganizationAlreadyExists;
import com.michael.organization.exception.payload.OrganizationNotFoundException;
import com.michael.organization.payload.request.OrganizationRequest;
import com.michael.organization.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationResponse> saveOrganization(@Valid @RequestBody OrganizationRequest organizationRequest) throws OrganizationAlreadyExists, OrganizationNotFoundException {
        return new ResponseEntity<>(organizationService.saveOrganization(organizationRequest), CREATED);
    }

    @GetMapping("/{organizationCode}")
    public ResponseEntity<OrganizationResponse> getOrganizationByCode(@PathVariable String organizationCode) throws OrganizationNotFoundException {
        return new ResponseEntity<>(organizationService.getOrganizationByCode(organizationCode), OK);
    }

    @GetMapping
    public ResponseEntity<List<OrganizationResponse>> getAllOrganization() {
        return new ResponseEntity<>(organizationService.getAllOrganization(), OK);
    }

}
