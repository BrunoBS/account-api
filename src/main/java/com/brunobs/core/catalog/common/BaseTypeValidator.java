package com.brunobs.core.catalog.common;

import com.brunobs.shared.BaseDTO;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.BaseRepository;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.lang.reflect.ParameterizedType;

/**
 * Abstract validator for catalog-type entities with i18n support.
 */
public abstract class BaseTypeValidator<
        E extends Enum<E> & BaseEnum<E>,
        R extends BaseRepository<?, Long>,
        DTO extends BaseDTO<String, Long>>
        extends BaseValidator<DTO, Long> {

    // Chaves de tradução (definidas nos arquivos .properties)
    public static final String MSG_DESCRIPTION_REQUIRED = "error.catalog.description.required";
    public static final String MSG_DESCRIPTION_INVALID_LENGTH = "error.catalog.description.invalid.length";
    public static final String MSG_DUPLICATE_NAME = "error.catalog.name.duplicate";
    public static final String MSG_INVALID_NAME = "error.catalog.name.invalid";

    protected final R repository;
    protected final Class<E> enumClass;

    protected BaseTypeValidator(R repository, Class<E> enumClass, MessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
        this.enumClass = enumClass;
    }

    @Override
    public void validateAttributes(DTO dto, ValidationResult vr) {

        E enumValue = getEnum(getName(dto));
        if (enumValue == null) {
            vr.addError("name", getMessage(MSG_INVALID_NAME, "name", BaseEnum.getOptionsValid(enumClass, messageSource)));
        }
        String description = getDescription(dto);
        String label = getLabel(dto);
        if (description == null || description.isBlank()) {
            vr.addError("description", getMessage(MSG_DESCRIPTION_REQUIRED));
        }
        if (description == null || description.isBlank()) {
            vr.addError("description", getMessage(MSG_DESCRIPTION_REQUIRED));
        } else if (description.length() < 3 || description.length() > 250) {
            vr.addError("description", getMessage(MSG_DESCRIPTION_INVALID_LENGTH));
        }

        validateAdditionalFields(dto, vr);
    }

    @Override
    public void validateIntegrity(DTO dto, ValidationResult vr) {
        Long id = dto.registrationIdentifier() == null ? 0L : dto.registrationIdentifier();

        if (repository.existsByNameAndIdNot(dto.registrationName(), id)) {
            vr.addError("name", getMessage(MSG_DUPLICATE_NAME));
        }
    }

    @Override
    protected boolean recordExists(Long id) {
        return repository.existsById(id);
    }



    // Extratores abstratos
    public abstract Long getId(DTO dto);
    public abstract String getName(DTO dto);
    public abstract String getDescription(DTO dto);
    public abstract String getLabel(DTO dto);
    public abstract E getEnum(String name);

    protected void validateAdditionalFields(DTO dto, ValidationResult vr) {}

    @Override
    protected String entityName() {
        try {
            return ((Class<?>) ((ParameterizedType) getClass()
                    .getGenericSuperclass())
                    .getActualTypeArguments()[0])
                    .getSimpleName();
        } catch (Exception e) {
            return "Entity";
        }
    }
}
