package com.brunobs.core.catalog.type.account;


import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class AccountTypeService extends BaseService<AccountType, AccountTypeDTO, Long> {

    public AccountTypeService(AccountTypeRepository repository,
                              AccountTypeMapper mapper,
                              AccountTypeValidator validator, CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);

    }

}
