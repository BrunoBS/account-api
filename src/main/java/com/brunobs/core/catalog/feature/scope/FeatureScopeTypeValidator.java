package com.brunobs.core.catalog.feature.scope;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.feature.type.FeatureTypeDTO;
import com.brunobs.core.catalog.feature.type.FeatureTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.base.BaseEnum;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Validator for FeatureScopeType catalog.
 * Ensures the DTO complies with business rules and technical Enum constants.
 */
@Component
public class FeatureScopeTypeValidator extends BaseTypeValidator<
        FeatureScopeTypeEnum,
        FeatureScopeTypeRepository,
        FeatureScopeTypeDTO> {

    public FeatureScopeTypeValidator(FeatureScopeTypeRepository repository,
                                     CatalogMessages catalogMessages,
                                     SchemaValidator schemaEngine,
                                     SchemaTypeRepository schemaTypeRepository) {
        super(repository, FeatureScopeTypeEnum.class, catalogMessages, schemaEngine, schemaTypeRepository);
    }

    @Override
    public Long getId(FeatureScopeTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(FeatureScopeTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(FeatureScopeTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(FeatureScopeTypeDTO dto) {
        return dto.description();
    }

    @Override
    public FeatureScopeTypeEnum getEnum(String name) {
        return BaseEnum.from(FeatureScopeTypeEnum.class, name);
    }

    @Override
    public JsonNode getSettings(FeatureScopeTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.TYPE;
    }
}

