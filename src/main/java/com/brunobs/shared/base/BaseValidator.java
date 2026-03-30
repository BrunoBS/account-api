package com.brunobs.shared.base;


import com.brunobs.exception.ValidationException;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;


public abstract class BaseValidator<DTO extends BaseDTO<String, ID>, ID> {

    protected BaseValidator() {

    }

    public void validateForCreate(DTO dto) {
        ValidationResult vr = new ValidationResult();
        validateNull(dto, vr);
        validateAttributes(dto, vr);
        validateIntegrity(dto, vr);

        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        }
    }


    public void validateForUpdate(DTO dto) {
        ValidationResult vr = new ValidationResult();
        validateNull(dto, vr);
        validateAttributes(dto, vr);
        validateIntegrity(dto, vr);

        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        }
    }


    public void validateNull(DTO dto, ValidationResult vr) {
        if (dto == null) {
            vr.addError(entityName(), recordRequired());
            throw new ValidationException(vr);
        }
    }

    public void validateForDelete(ID id) {
    }


    public void validateAdditionalFields(DTO dto, ValidationResult vr) {
    }


    protected String getListOr(List<? extends Enum> enumList, MessageSource messageSource) {
        List<String> options = enumList.stream().map(Enum::name).toList();

        if (options.isEmpty()) return "";
        if (options.size() == 1) return options.get(0);

        String separator = messageSource.getMessage(BaseEnum.COMMON_LABEL_OR, null, LocaleContextHolder.getLocale());

        int lastIdx = options.size() - 1;
        return String.join(", ", options.subList(0, lastIdx))
                + " " + separator + " " + options.get(lastIdx);

    }

    public abstract String entityName();

    protected abstract void validateIntegrity(DTO dto, ValidationResult vr);

    protected abstract void validateAttributes(DTO dto, ValidationResult vr);

    public abstract String recordRequired();

}
