package com.brunobs.shared.validation;


import com.brunobs.exception.ValidationException;
import com.brunobs.shared.BaseDTO;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Core validation engine for all system DTOs.
 * Coordinates attribute, integrity, and existence checks.
 */
public abstract class BaseValidator<DTO extends BaseDTO<String, ID>, ID> {

    // Chaves de tradução para internacionalização
    public static final String MSG_DTO_NULL = "error.validation.dto.null";
    public static final String MSG_NOT_FOUND = "error.validation.record.not.found";

    protected final MessageSource messageSource;

    protected BaseValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Standard flow for creating a new record.
     */
    public void validateForCreate(DTO dto) {
        ValidationResult vr = new ValidationResult();
        validateNull(dto, vr);
        validateAttributes(dto, vr);
        validateIntegrity(dto, vr);

        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        }
    }

    /**
     * Standard flow for updating an existing record.
     */
    public void validateForUpdate(DTO dto) {
        ValidationResult vr = new ValidationResult();
        validateNull(dto, vr);
        if (dto.registrationIdentifier() == null) {
            vr.addError("id", getMessage("error.validation.id.required"));
            throw new ValidationException(vr);
        }
        validateExistence(dto.registrationIdentifier());
        validateAttributes(dto, vr);
        validateIntegrity(dto, vr);

        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        }
    }

    /**
     * Ensures the record exists in the database.
     */
    public void validateExistence(ID id) {
        if (id == null || !recordExists(id)) {
            ValidationResult vr = new ValidationResult();
            vr.addError("id", getMessage(MSG_NOT_FOUND));
            throw new ValidationException(vr);
        }
    }

    public void validateNull(DTO dto, ValidationResult vr) {
        if (dto == null) {
            vr.addError(entityName(), getMessage(MSG_DTO_NULL));
            throw new ValidationException(vr);
        }
    }

    public void validateForDelete(ID id) {
        validateExistence(id);
    }


    protected void validateAdditionalFields(DTO dto, ValidationResult vr) {
    }


    protected String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }


    protected abstract String entityName();
    protected abstract void validateIntegrity(DTO dto, ValidationResult vr);
    protected abstract void validateAttributes(DTO dto, ValidationResult vr);
    protected abstract boolean recordExists(ID id);
}
