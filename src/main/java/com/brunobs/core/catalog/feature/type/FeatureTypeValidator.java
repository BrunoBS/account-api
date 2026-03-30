package com.brunobs.core.catalog.feature.type;

import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeEnum;
import com.brunobs.core.catalog.type.account.AccountTypeDTO;
import com.brunobs.core.catalog.type.account.AccountTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeEnum;
import com.brunobs.core.catalog.type.schema.SchemaTypeRepository;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FeatureTypeValidator extends BaseTypeValidator<
        FeatureTypeEnum,
        FeatureTypeRepository,
        FeatureTypeDTO> {


    public FeatureTypeValidator(FeatureTypeRepository repository,
                                CatalogMessages catalogMessages,
                                SchemaValidator schemaEngine,
                                SchemaTypeRepository schemaTypeRepository) {
        super(repository, FeatureTypeEnum.class, catalogMessages, schemaEngine, schemaTypeRepository);

    }

    @Override
    public Long getId(FeatureTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(FeatureTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getDescription(FeatureTypeDTO dto) {
        return dto.description();
    }

    @Override
    public String getLabel(FeatureTypeDTO dto) {
        return dto.label();
    }

    @Override
    public JsonNode getSettings(FeatureTypeDTO dto) {
        return dto.settings();
    }

    @Override
    public SchemaTypeEnum getTypeSchema() {
        return SchemaTypeEnum.FEATURE;
    }

    @Override
    public FeatureTypeEnum getEnum(String name) {
        return BaseEnum.from(FeatureTypeEnum.class, name);
    }

    @Override
    public void validateAdditionalFields(FeatureTypeDTO dto, ValidationResult vr) {

        FeatureScopeTypeEnum scopeEnum = BaseEnum.from(FeatureScopeTypeEnum.class, dto.scope());

        if (scopeEnum == null) {
            List<String> opcoesValidas = Arrays.stream(FeatureScopeTypeEnum.values()).map(Enum::name).toList();
            String optionsValid = BaseEnum.getOptionsValid(opcoesValidas, AccountTypeEnum.class, catalogMessages.getMessageSource());
            vr.addError("Scope", catalogMessages.scopeInvalid(entityName(), optionsValid));

        } else {

            FeatureTypeEnum typeEnum = getEnum(dto.name());
            if (typeEnum != null) {
                List<FeatureTypeEnum> allowedTypes = scopeEnum.getAllowedFeatureTypes();
                if (!allowedTypes.contains(typeEnum)) {
                    List<String> list = allowedTypes.stream().map(String::valueOf).toList();
                    String optionsValid = BaseEnum.getOptionsValid(list, FeatureTypeEnum.class, catalogMessages.getMessageSource());
                    vr.addError("name", catalogMessages.nameInvalid(entityName(), optionsValid));
                }
            }
        }

    }

    @Override
    public void validateIntegrity(FeatureTypeDTO dto, ValidationResult vr) {
        Long id = dto.registrationIdentifier() == null ? 0L : dto.registrationIdentifier();

        if (repository.existsByNameAndFeatureScopeNameAndIdNot(dto.name(), dto.scope(), id)) {
            vr.addError("name", catalogMessages.nameDuplicate(entityName(), dto.name() + "/" + dto.scope()));
        }
    }

}
