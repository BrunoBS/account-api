package com.brunobs.message.feature;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ApplicationMessages extends MessageAbstract {

    private static final String APPLICATION_REQUIRED = "application.required";
    private final String NAME_REQUIRED = "application.name.required";
    private final String NAME_INVALID = "application.name.invalid";
    private final String NAME_DUPLICATED = "application.name.duplicated";
    private final String ALIAS_REQUIRED = "application.alias.required";
    private final String ACCOUNT_REQUIRED = "application.account.required";
    private final String NOT_FOUND = "application.not.found";
    private final String ALREADY_EXISTS = "application.already.exists";
    private final String LANGUAGE_INVALID = "application.language.invalid";
    private final String INFRA_INVALID = "application.infrastructure.invalid";
    private final String SCOPE_INVALID = "application.scope.invalid";
    private final String ACCOUNT_TYPE_UNAUTHORIZED = "application.account.type.unauthorized";




    public ApplicationMessages(MessageSource messageSource) {
        super(messageSource);
    }

    public String nameRequired() {
        return getMessage(NAME_REQUIRED);
    }

    public String nameInvalid() {

        return getMessage(NAME_INVALID);
    }

    public String nameDuplicated(String name) {

        return getMessage(NAME_DUPLICATED, name);
    }

    public String aliasRequired() {
        return getMessage(ALIAS_REQUIRED);
    }

    public String accountRequired() {
        return getMessage(ACCOUNT_REQUIRED);
    }


    public String applicationExists() {
        return getMessage(ALREADY_EXISTS);
    }

    public String notFound() {
        return getMessage(NOT_FOUND);
    }

    public String languageInvalid(String languagesNamesValid) {
        return getMessage(LANGUAGE_INVALID, languagesNamesValid);
    }

    public String infrastructureInvalid(String infrastructureNamesValid) {
        return getMessage(INFRA_INVALID, infrastructureNamesValid);
    }

    public String scopeInvalid(String scopeNamesValid) {
        return getMessage(SCOPE_INVALID, scopeNamesValid);
    }


    public String recordRequired() {
        return getMessage(APPLICATION_REQUIRED);
    }

    public String accountTypeUnauthorized(String currentAccountType) {
        return getMessage(ACCOUNT_TYPE_UNAUTHORIZED, currentAccountType);
    }


}
