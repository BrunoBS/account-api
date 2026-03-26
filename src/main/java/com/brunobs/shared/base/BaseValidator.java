package com.brunobs.shared.base;


import com.brunobs.exception.ValidationException;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;


public abstract class BaseValidator<DTO extends BaseDTO<String, ID>, ID> {


    public static final String MSG_DTO_NULL = "error.validation.dto.null";
    public static final String MSG_NOT_FOUND = "error.validation.record.not.found";
    public static final String MSG_NOT_FOUND_RESTORE = "error.validation.record.not.found.restore";

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
    }


    protected void validateAdditionalFields(DTO dto, ValidationResult vr) {
    }


    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    protected String getListOr(List<? extends Enum> enumList) {
        List<String> options = enumList.stream().map(Enum::name).toList();

        if (options.isEmpty()) return "";
        if (options.size() == 1) return options.get(0);

        // Busca o separador localizado ("ou" / "or") do messages_en.properties
        String separator = messageSource.getMessage(BaseEnum.COMMON_LABEL_OR, null, LocaleContextHolder.getLocale());

        int lastIdx = options.size() - 1;
        return String.join(", ", options.subList(0, lastIdx))
                + " " + separator + " " + options.get(lastIdx);

    }

    public abstract String entityName();

    protected abstract void validateIntegrity(DTO dto, ValidationResult vr);

    protected abstract void validateAttributes(DTO dto, ValidationResult vr);

    protected abstract boolean recordExists(ID id);
}
