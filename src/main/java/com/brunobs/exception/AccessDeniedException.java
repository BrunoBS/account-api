package com.brunobs.exception;


import com.brunobs.shared.validation.ValidationResult;

public class AccessDeniedException extends RuntimeException {

    private final ValidationResult validationResult;

    public AccessDeniedException(ValidationResult validationResult) {
        super("Validation failed with " + (validationResult != null ? validationResult.getErrors().size() : 0) + " error(s).");
        this.validationResult = validationResult;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }
}
