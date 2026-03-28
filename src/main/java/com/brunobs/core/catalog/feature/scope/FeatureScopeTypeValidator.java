package com.brunobs.core.catalog.feature.scope;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseEnum;
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

    public FeatureScopeTypeValidator(FeatureScopeTypeRepository repository, CatalogMessages catalogMessages) {
        super(repository, FeatureScopeTypeEnum.class, catalogMessages);
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
}
