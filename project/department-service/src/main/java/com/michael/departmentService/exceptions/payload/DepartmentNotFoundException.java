package com.michael.departmentService.exceptions.payload;

public class DepartmentNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
