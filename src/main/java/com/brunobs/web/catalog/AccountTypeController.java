package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.account.AccountType;
import com.brunobs.core.catalog.type.account.AccountTypeService;
import com.brunobs.shared.BaseController;
import com.brunobs.shared.BaseService;
import com.brunobs.core.catalog.type.account.AccountTypeDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account-type")
public class AccountTypeController extends BaseController<AccountTypeDTO, AccountType, Long> {

    private final AccountTypeService service;

    public AccountTypeController(AccountTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<AccountType, AccountTypeDTO, Long> getService() {
        return service;
    }
}
