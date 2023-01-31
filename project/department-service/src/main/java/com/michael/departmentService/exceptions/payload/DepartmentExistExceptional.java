package com.michael.departmentService.exceptions.payload;

public class DepartmentExistExceptional  extends Exception{
    private static final long serialVersionUID = 1L;
    public DepartmentExistExceptional(String message) {
        super(message);
    }
}
