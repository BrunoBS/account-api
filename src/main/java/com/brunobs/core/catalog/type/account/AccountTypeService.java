package com.brunobs.core.catalog.type.account;



import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class AccountTypeService extends BaseService<AccountType, AccountTypeDTO, Long> {

    public AccountTypeService(AccountTypeRepository repository,
                              AccountTypeMapper mapper,
                              AccountTypeValidator validator,
                              MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
    }


    @Override
    public String getServiceIdentifier() {
        return "AccountType";
    }
}
