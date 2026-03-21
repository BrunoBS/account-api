package com.brunobs.core.catalog.type.account;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.shared.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Validator for AccountType catalog.
 * Ensures the DTO complies with business rules before saving.
 */
@Component
public class AccountTypeValidator extends BaseTypeValidator<
        AccountTypeEnum,
        AccountTypeRepository,
        AccountTypeDTO> {

    public AccountTypeValidator(AccountTypeRepository repository, MessageSource messageSource) {
        super(repository, AccountTypeEnum.class, messageSource);
    }

    @Override
    public Long getId(AccountTypeDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(AccountTypeDTO dto) {
        return dto.name();
    }

    @Override
    public String getDescription(AccountTypeDTO dto) {
        return dto.description();
    }

    @Override
    public String getLabel(AccountTypeDTO dto) {
        return  dto.label();
    }

    @Override
    public AccountTypeEnum getEnum(String name) {
        return BaseEnum.from(AccountTypeEnum.class, name);
    }
}
