package de.clerkvest.api.exception;

public class CompanyNotMatchException extends RuntimeException {

    public CompanyNotMatchException(String message) {
        super(message);
    }
}