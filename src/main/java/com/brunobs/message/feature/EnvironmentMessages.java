package com.brunobs.message.feature;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentMessages extends MessageAbstract {

    private final String NAME_REQUIRED = "environment.name.required";
    private final String NAME_INVALID = "environment.name.invalid";
    private final String DESC_REQUIRED = "environment.description.required";
    private final String DESC_INVALID = "environment.description.invalid";
    private final String CUSTOM_MISSING_ACCOUNT = "environment.custom.missing.account";
    private final String DUPLICATED_DEFAULT = "environment.duplicated.default";
    private final String DUPLICATED_CUSTOM = "environment.duplicated.custom";
    private final String ACCOUNT_INVALID_CUSTOM = "environment.account.invalid.custom";
    private final String ACCOUNT_INVALID_DEFAULT= "environment.account.invalid.default";
    private final String TYPE_INVALID = "environment.type.invalid";
    private final String AUTHORIZATION_INVALID = "environment.authorization.invalid";
    private static final String NOT_FOUND = "environment.not.found";
    private static final String RESTORE_INVALIDED = "environment.restore.invalid";

    public String typeInvalid(String validTypes) {
        return getMessage(TYPE_INVALID, validTypes);
    }

    public String authorizationInvalid(String validAuthTypes) {
        return getMessage(AUTHORIZATION_INVALID, validAuthTypes);
    }

    public EnvironmentMessages(MessageSource messageSource) {
        super(messageSource);
    }

    public String nameRequired() {
        return getMessage(NAME_REQUIRED);
    }

    public String nameInvalid(int min, int max) {
        return getMessage(NAME_INVALID, min, max);
    }

    public String descriptionRequired() {
        return getMessage(DESC_REQUIRED);
    }

    public String descriptionInvalid(int min, int max) {
        return getMessage(DESC_INVALID, min, max);
    }

    public String customMissingAccount() {
        return getMessage(CUSTOM_MISSING_ACCOUNT);
    }

    public String duplicatedDefault(String name) {
        return getMessage(DUPLICATED_DEFAULT, name);
    }

    public String duplicatedCustom(String name) {
        return getMessage(DUPLICATED_CUSTOM, name);
    }

    public String accountInvalidCustom() {
        return getMessage(ACCOUNT_INVALID_CUSTOM);
    }
    public String accountInvalidDefault() {
        return getMessage(ACCOUNT_INVALID_DEFAULT);
    }

    public String notFound() {
        return getMessage(NOT_FOUND);
    }

    public String restoreInvalided() {
        return getMessage(RESTORE_INVALIDED);
    }
}
