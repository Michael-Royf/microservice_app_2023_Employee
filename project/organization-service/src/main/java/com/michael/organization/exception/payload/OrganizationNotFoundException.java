package com.michael.organization.exception.payload;

public class OrganizationNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public OrganizationNotFoundException(String message) {
        super(message);
    }
}
