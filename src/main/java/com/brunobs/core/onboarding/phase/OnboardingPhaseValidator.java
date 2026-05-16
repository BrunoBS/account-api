package com.brunobs.core.onboarding.phase;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.base.BaseEnum;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;


@Component
public class OnboardingPhaseValidator extends BaseTypeValidator<
        OnboardingPhaseEnum,
        OnboardingPhaseRepository,
        OnboardingPhaseDTO> {

    public OnboardingPhaseValidator(OnboardingPhaseRepository repository,
                                    SchemaTypeRepository schemaTypeRepository,
                                    SchemaValidator schemaValidator,
                                    CatalogMessages catalogMessages
    ) {
        super(repository, OnboardingPhaseEnum.class, catalogMessages, schemaValidator, schemaTypeRepository);
    }

    @Override
    public String getName(OnboardingPhaseDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(OnboardingPhaseDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(OnboardingPhaseDTO dto) {
        return dto.description();
    }

    @Override
    public OnboardingPhaseEnum getEnum(String name) {
        return BaseEnum.from(OnboardingPhaseEnum.class, name);
    }

    @Override
    public JsonNode getSettings(OnboardingPhaseDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.TYPE;
    }
}
