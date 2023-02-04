package com.michael.organization.exception.payload;

public class OrganizationAlreadyExists  extends  Exception{
    private static final long serialVersionUID = 1L;

    public OrganizationAlreadyExists(String message) {
        super(message);
    }
}
