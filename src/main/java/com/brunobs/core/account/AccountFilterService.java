package com.brunobs.core.account;

import com.brunobs.filter.AbstractFilterService;
import com.brunobs.filter.AuthorizationContext;
import org.springframework.stereotype.Service;

@Service
public class AccountFilterService extends AbstractFilterService<Account> {


    @Override
    public boolean authorize(Account item, AuthorizationContext context) {

        String requiredGroup =
                context.getAuthorizationTypeName() + "_" + context.getAuthorizerGroup();

        return context.getUserContext()
                .getGroups()
                .contains(requiredGroup);
    }
}