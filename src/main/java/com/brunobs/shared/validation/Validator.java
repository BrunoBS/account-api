package com.brunobs.shared.validation;

public interface Validator<T> {
    ValidationResult validate(T target);
}
