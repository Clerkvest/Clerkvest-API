package de.clerkvest.api.exception;

public class ViolatedConstraintException extends RuntimeException {

    public ViolatedConstraintException(String message) {
        super(message);
    }
}
