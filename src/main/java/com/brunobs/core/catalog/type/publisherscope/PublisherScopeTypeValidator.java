package com.brunobs.core.catalog.type.publisherscope;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.shared.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Validator for PublisherScopeType catalog.
 * Ensures the DTO complies with business rules and technical Enum constants.
 */
@Component
public class PublisherScopeTypeValidator extends BaseTypeValidator<
        PublisherScopeTypeEnum,
        PublisherScopeTypeRepository,
        PublisherScopeTypeDTO> {

    public PublisherScopeTypeValidator(PublisherScopeTypeRepository repository, MessageSource messageSource) {
        super(repository, PublisherScopeTypeEnum.class, messageSource);
    }

    @Override
    public Long getId(PublisherScopeTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(PublisherScopeTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(PublisherScopeTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(PublisherScopeTypeDTO dto) {
        return dto.description();
    }

    @Override
    public PublisherScopeTypeEnum getEnum(String name) {
        return BaseEnum.from(PublisherScopeTypeEnum.class, name);
    }
}
