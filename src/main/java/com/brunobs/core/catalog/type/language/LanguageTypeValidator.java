package com.brunobs.core.catalog.type.language;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.shared.base.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class LanguageTypeValidator extends BaseTypeValidator<
        LanguageTypeEnum,
        LanguageTypeRepository,
        LanguageTypeDTO> {

    public LanguageTypeValidator(LanguageTypeRepository repository, MessageSource messageSource) {
        super(repository, LanguageTypeEnum.class, messageSource);
    }

    @Override
    public Long getId(LanguageTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(LanguageTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(LanguageTypeDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(LanguageTypeDTO dto) {
        return dto.description();
    }

    @Override
    public LanguageTypeEnum getEnum(String name) {
        return BaseEnum.from(LanguageTypeEnum.class, name);
    }
}
