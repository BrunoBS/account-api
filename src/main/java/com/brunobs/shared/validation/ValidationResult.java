package com.brunobs.shared.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private final List<FieldError> errors = new ArrayList<>();

    public ValidationResult() {
    }

    public ValidationResult(String field, String message) {
        errors.add(new FieldError(field, message));
    }

    public void addError(String field, String message) {
        errors.add(new FieldError(field, message));
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    // Permite juntar erros de outro ValidationResult
    public void merge(ValidationResult other) {
        if (other != null) {
            this.errors.addAll(other.getErrors());
        }
    }
}
