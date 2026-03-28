package com.brunobs.core.catalog.common;

import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseDTO;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.base.BaseRepository;
import com.brunobs.shared.base.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;

import java.lang.reflect.ParameterizedType;

/**
 * Abstract validator for catalog-type entities with i18n support.
 */
public abstract class BaseTypeValidator<
        E extends Enum<E> & BaseEnum<E>,
        R extends BaseRepository<?, Long>,
        DTO extends BaseDTO<String, Long>>
        extends BaseValidator<DTO, Long> {


    protected final R repository;
    protected final Class<E> enumClass;
    protected final CatalogMessages catalogMessages;

    protected BaseTypeValidator(R repository, Class<E> enumClass, CatalogMessages catalogMessages) {

        this.repository = repository;
        this.enumClass = enumClass;
        this.catalogMessages = catalogMessages;
    }

    @Override
    public void validateAttributes(DTO dto, ValidationResult vr) {
        String catalogNamesValid = BaseEnum.getOptionsValid(enumClass, catalogMessages.getMessageSource());
        E enumValue = getEnum(getName(dto));
        if (enumValue == null) {
            vr.addError("name", catalogMessages.nameInvalid(entityName(), catalogNamesValid));

        }
        String description = getDescription(dto);
        String label = getLabel(dto);
        if (label == null || label.isBlank()) {
            vr.addError("description", catalogMessages.labelRequired(entityName()));
        }
        if (description == null || description.isBlank()) {
            vr.addError("description", catalogMessages.descriptionRequired(entityName()));
        } else if (description.length() < 3 || description.length() > 250) {
            vr.addError("description", catalogMessages.descriptionInvalidLength(entityName(), 3, 250));
        }
        validateAdditionalFields(dto, vr);
    }

    @Override
    public void validateIntegrity(DTO dto, ValidationResult vr) {
        Long id = dto.registrationIdentifier() == null ? 0L : dto.registrationIdentifier();

        if (repository.existsByNameAndIdNot(dto.registrationName(), id)) {
            vr.addError("name", catalogMessages.nameDuplicate(entityName(), dto.registrationName()));
        }
    }


    public abstract Long getId(DTO dto);

    public abstract String getName(DTO dto);

    public abstract String getDescription(DTO dto);

    public abstract String getLabel(DTO dto);

    public abstract E getEnum(String name);

    @Override
    public String entityName() {
        try {
            String name = ((Class<?>) ((ParameterizedType) getClass()
                    .getGenericSuperclass())
                    .getActualTypeArguments()[0])
                    .getSimpleName();

            if (name.endsWith("Enum")) {
                name = name.substring(0, name.length() - "Enum".length());
            }

            return name;
        } catch (Exception e) {
            return "Entity";
        }
    }

    @Override
    public String recordRequired() {
        return catalogMessages.recordRequired();
    }
}
