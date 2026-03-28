package com.brunobs.core.publisher;


import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeEnum;
import com.brunobs.message.feature.PublisherMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.base.BaseValidator;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Validator for Publisher domain.
 * Ensures data integrity, business rules for types, and JSON Schema syntax.
 */
@Component
public class PublisherValidator extends BaseValidator<PublisherDTO, Long> {


    private final PublisherRepository repository;
    private final SchemaValidator schemaEngine;
    private final PublisherMessages publisherMessages;

    public PublisherValidator(PublisherRepository repository,
                              SchemaValidator schemaEngine, PublisherMessages publisherMessages) {

        this.repository = repository;
        this.schemaEngine = schemaEngine;
        this.publisherMessages = publisherMessages;
    }

    @Override
    protected void validateAttributes(PublisherDTO dto, ValidationResult vr) {
        PublisherTypeEnum publisherTypeEnum = getPublisherTypeEnum(dto.name());
        PublisherScopeTypeEnum publisherScopeTypeEnum = getPublisherScopeTypeEnum(dto.name());

        if (publisherTypeEnum == null) {
            List<PublisherTypeEnum> opcoesValidas = Arrays.stream(PublisherTypeEnum.values()).toList();
            String validNames = getListOr(opcoesValidas, publisherMessages.getMessageSource());
            vr.addError("name", publisherMessages.nameInvalid(validNames));
        }

        if (publisherScopeTypeEnum == null) {
            List<PublisherScopeTypeEnum> opcoesValidas = Arrays.stream(PublisherScopeTypeEnum.values()).toList();
            String validNames = getListOr(opcoesValidas, publisherMessages.getMessageSource());
            vr.addError("publisherScope", publisherMessages.scopeInvalid(validNames));
        }


        if (dto.label() == null || dto.label().length() < 3 || dto.label().length() > 50) {
            vr.addError("label", publisherMessages.labelRequired());
        }


        if (dto.description() == null || dto.description().isBlank()) {
            vr.addError("description", publisherMessages.descriptionRequired());
        }

        validateAdditionalFields(dto, vr);
    }

    @Override
    public String recordRequired() {
        return publisherMessages.recordRequired();
    }


    @Override
    protected void validateAdditionalFields(PublisherDTO dto, ValidationResult vr) {
        schemaEngine.validateSchemaSyntax(dto.jsonSchema(), vr);
    }

    @Override
    protected void validateIntegrity(PublisherDTO dto, ValidationResult vr) {
        Long id = dto.id() == null ? 0L : dto.id();
        if (repository.existsByNameAndIdNot(dto.name(), id)) {
            vr.addError("name", publisherMessages.nameDuplicate(dto.name()));
        }
    }

    @Override
    public String entityName() {
        return Publisher.class.getSimpleName();
    }

    private PublisherTypeEnum getPublisherTypeEnum(String name) {
        return BaseEnum.from(PublisherTypeEnum.class, name);
    }

    private PublisherScopeTypeEnum getPublisherScopeTypeEnum(String name) {
        return BaseEnum.from(PublisherScopeTypeEnum.class, name);
    }
}
