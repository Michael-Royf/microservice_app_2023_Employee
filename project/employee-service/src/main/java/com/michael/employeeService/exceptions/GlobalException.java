package com.michael.employeeService.exceptions;

import com.michael.employeeService.exceptions.payload.EmailExistException;
import com.michael.employeeService.exceptions.payload.EmployeeNotFoundException;
import com.michael.employeeService.payload.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
    private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
    private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    public static final String ERROR_PATH = "/error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> handleMethodGlobalException(Exception ex) {
        return createHttpResponse(BAD_REQUEST, ex.getMessage());
    }


    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> usernameExistException(EmailExistException exception) {
        return createHttpResponse(CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<HttpResponse> usernameExistException(EmployeeNotFoundException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }




    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(
                httpStatus.value(),
                httpStatus,
                message),
                httpStatus);
    }
}
