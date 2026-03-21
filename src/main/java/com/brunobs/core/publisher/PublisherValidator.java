package com.brunobs.core.publisher;


import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeEnum;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Validator for Publisher domain.
 * Ensures data integrity, business rules for types, and JSON Schema syntax.
 */
@Component
public class PublisherValidator extends BaseValidator<PublisherDTO, Long> {

    // Constantes de Mensagens (Chaves i18n)
    public static final String MSG_NAME_INVALID = "error.publisher.name.invalid";
    public static final String MSG_LABEL_INVALID = "error.publisher.label.invalid";
    public static final String MSG_DESCRIPTION_REQUIRED = "error.publisher.description.required";
    public static final String MSG_SCOPE_INVALID = "error.publisher.scope.invalid";
    public static final String MSG_DUPLICATE_NAME = "error.publisher.duplicate.name";

    private final PublisherRepository repository;
    private final SchemaValidator schemaEngine;

    public PublisherValidator(PublisherRepository repository,
                              SchemaValidator schemaEngine,
                              MessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
        this.schemaEngine = schemaEngine;
    }

    @Override
    protected void validateAttributes(PublisherDTO dto, ValidationResult vr) {
        // 1. Validate Publisher Type (Name)
        if (BaseEnum.from(PublisherTypeEnum.class, dto.name()) == null) {
            vr.addError("name", getMessage(MSG_NAME_INVALID, "name",
                    BaseEnum.getOptionsValid(PublisherTypeEnum.class, messageSource)));
        }

        // 2. Validate Label length
        if (dto.label() == null || dto.label().length() < 3 || dto.label().length() > 50) {
            vr.addError("label", getMessage(MSG_LABEL_INVALID));
        }

        // 3. Validate Description
        if (dto.description() == null || dto.description().isBlank()) {
            vr.addError("description", getMessage(MSG_DESCRIPTION_REQUIRED));
        }

        // 4. Validate Publisher Scope
        if (BaseEnum.from(PublisherScopeTypeEnum.class, dto.publisherScope()) == null) {
            vr.addError("publisherScope", getMessage(MSG_SCOPE_INVALID));
        }

        validateAdditionalFields(dto, vr);
    }

    @Override
    protected void validateAdditionalFields(PublisherDTO dto, ValidationResult vr) {
        schemaEngine.validateSchemaSyntax(dto.jsonSchema(), vr);
    }

    @Override
    protected void validateIntegrity(PublisherDTO dto, ValidationResult vr) {
        Long id = dto.id() == null ? 0L : dto.id();
        if (repository.existsByNameAndIdNot(dto.name(), id)) {
            vr.addError("name", getMessage(MSG_DUPLICATE_NAME));
        }
    }

    @Override
    protected String entityName() {
        return Publisher.class.getSimpleName();
    }

    @Override
    protected boolean recordExists(Long id) {
        return repository.existsById(id);
    }
}
