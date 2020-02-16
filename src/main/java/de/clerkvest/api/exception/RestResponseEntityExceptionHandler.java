package de.clerkvest.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {ClerkEntityNotFoundException.class, DuplicateEntityException.class, ViolatedConstraintException.class, NotEnoughPermissionsException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        if (ex instanceof ClerkEntityNotFoundException) {
            String bodyOfResponse = ex.getMessage();
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        } else if (ex instanceof DuplicateEntityException) {
            String bodyOfResponse = ex.getMessage();
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.CONFLICT, request);
        } else if (ex instanceof ViolatedConstraintException) {
            String bodyOfResponse = ex.getMessage();
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.CONFLICT, request);
        } else if (ex instanceof NotEnoughPermissionsException) {
            String bodyOfResponse = ex.getMessage();
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.FORBIDDEN, request);
        }
        String bodyOfResponse = "A Error Occurred";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<Object> handleConstraintViolation(Exception ex, WebRequest request) {
        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        if (cause instanceof ConstraintViolationException) {
            //Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
            String bodyOfResponse = ex.getMessage();
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }
        String bodyOfResponse = "A Error Occurred";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
