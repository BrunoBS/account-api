package com.brunobs.message.feature;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class AccountEnvMessages extends MessageAbstract {

    private final String DUPLICATE_PUBLISHER = "account.env.duplicate.publisher";
    private final String INCONSISTENT_ACCOUNT = "account.env.inconsistent.account";
    private final String MIN_PUBLISHER = "account.env.min.publisher";
    private final String ORDER_REQUIRED = "account.env.order.required";
    private final String ACCOUNT_ENV_REQUIRED = "account.env.required";
    private final String NOT_FOUND = "account.env.not.found";
    private final String SELECTION_REQUIRED = "account.env.selection.required";
    private final String INVALID_PUBLISHER_SCOPE = "account.env.publisher.scope.invalid";

    public AccountEnvMessages(MessageSource messageSource) {
        super(messageSource);
    }

    public String duplicatePublisher() {
        return getMessage(DUPLICATE_PUBLISHER);
    }

    public String invalidPublisherScope() {
        return getMessage(INVALID_PUBLISHER_SCOPE);
    }

    public String inconsistentAccount(Object accountName) {
        return getMessage(INCONSISTENT_ACCOUNT, accountName);
    }

    public String minPublisher(int min) {
        return getMessage(MIN_PUBLISHER, min);
    }

    public String orderRequired() {
        return getMessage(ORDER_REQUIRED);
    }


    public String recordRequired() {
        return getMessage(ACCOUNT_ENV_REQUIRED);
    }

    public String notFound() {
        return getMessage(NOT_FOUND);
    }

    public String selectionRequired() {
        return getMessage(SELECTION_REQUIRED);
    }

}
