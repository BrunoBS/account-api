package com.brunobs.core.catalog.type.environment;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.authorization.AuthorizationTypeDTO;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.core.onboarding.phase.OnboardingPhaseEnum;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.base.BaseEnum;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentTypeValidator extends BaseTypeValidator<EnvironmentTypeEnum, EnvironmentTypeRepository, EnvironmentTypeDTO> {


    public EnvironmentTypeValidator(EnvironmentTypeRepository repository,
                                    SchemaTypeRepository schemaTypeRepository,
                                    SchemaValidator schemaValidator,
                                    CatalogMessages catalogMessages
    ) {
        super(repository, EnvironmentTypeEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);
    }

    @Override
    public Long getId(EnvironmentTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(EnvironmentTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getDescription(EnvironmentTypeDTO dto) {
        return dto.description();
    }

    @Override
    public String getLabel(EnvironmentTypeDTO dto) {
        return dto.label();
    }

    @Override
    public EnvironmentTypeEnum getEnum(String name) {
        return BaseEnum.from(EnvironmentTypeEnum.class, name);
    }

    @Override
    public JsonNode getSettings(EnvironmentTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.TYPE;
    }
}
