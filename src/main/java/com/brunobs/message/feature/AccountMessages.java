package com.brunobs.message.feature;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class AccountMessages extends MessageAbstract {


    private static final String ACCOUNT_REQUIRED = "account.required";
    private static final String NAME_REQUIRED = "account.name.required";
    private static final String NAME_DUPLICATED = "account.name.duplicated";
    private static final String NAME_INVALID = "account.name.invalid";
    private static final String DESC_INVALID = "account.description.invalid";
    private static final String REQUESTER_INVALID = "account.requester.invalid";
    private static final String INITIALS_REQUIRED = "account.initials.required";
    private static final String INITIALS_INVALID = "account.initials.invalid";
    private static final String EMAIL_GROUP_INVALID = "account.email.group.invalid";
    private static final String APPROVERS_REQUIRED = "account.approvers.required";
    private static final String FUNCIONAL_REQUIRED = "account.funcional.required";
    private static final String EMAIL_INVALID = "account.email.invalid";
    private static final String RESTORE_INVALID = "account.restore.invalid";
    private static final String NOT_FOUND = "account.not.found";
    private static final String ALREADY_EXISTS = "account.already.exists";
    private final String TYPE_INVALID = "account.type.invalid";

    public AccountMessages(MessageSource messageSource) {
        super(messageSource);
    }

    // Métodos de Acesso (Instanciáveis ou Estáticos conforme sua necessidade)


    public String nameRequired() {
        return getMessage(NAME_REQUIRED);
    }

    public String nameDuplicated(String name) {
        return getMessage(NAME_DUPLICATED, name);
    }

    public String nameInvalid() {
        return getMessage(NAME_INVALID);
    }

    public String descriptionInvalid() {
        return getMessage(DESC_INVALID);
    }

    public String requesterInvalid() {
        return getMessage(REQUESTER_INVALID);
    }

    public String initialsRequired() {
        return getMessage(INITIALS_REQUIRED);
    }

    public String initialsInvalid() {
        return getMessage(INITIALS_INVALID);
    }

    public String emailGroupInvalid() {
        return getMessage(EMAIL_GROUP_INVALID);
    }

    public String approversRequired() {
        return getMessage(APPROVERS_REQUIRED);
    }

    public String funcionalRequired() {
        return getMessage(FUNCIONAL_REQUIRED);
    }

    public String emailInvalid(String email) {
        return getMessage(EMAIL_INVALID, email);
    }

    public String restoreInvalid() {
        return getMessage(RESTORE_INVALID);
    }

    public String notFound() {
        return getMessage(NOT_FOUND);
    }

    public String accountExists() {
        return getMessage(ALREADY_EXISTS);
    }

    public String recordRequired() {
        return getMessage(ACCOUNT_REQUIRED);
    }

    public String typeInvalid(String typeValid) {
        return getMessage(TYPE_INVALID, typeValid);
    }


}

