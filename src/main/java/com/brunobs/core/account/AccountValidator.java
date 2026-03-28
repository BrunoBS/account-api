package com.brunobs.core.account;

import com.brunobs.core.catalog.type.account.AccountTypeEnum;
import com.brunobs.message.feature.AccountMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.base.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class AccountValidator extends BaseValidator<AccountDTO, Long> {


    private final AccountRepository repository;
    private final AccountMessages accountMessages;

    public AccountValidator(AccountRepository repository, MessageSource messageSource, AccountMessages accountMessages) {
        this.repository = repository;
        this.accountMessages = accountMessages;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");

    @Override
    public void validateAttributes(AccountDTO dto, ValidationResult vr) {
        AccountTypeEnum typeEnum = getAccountTypeEnum(dto.accountType());
        if (typeEnum == null || AccountTypeEnum.CATALOG.equals(typeEnum)) {
            List<AccountTypeEnum> opcoesValidas = Arrays.asList(AccountTypeEnum.ADMIN, AccountTypeEnum.MANAGER);
            String accountTypesValid = getListOr(opcoesValidas, accountMessages.getMessageSource());
            vr.addError("accountType", accountMessages.typeInvalid(accountTypesValid));

        }

        if (dto.name() == null || dto.name().isBlank()) {
            vr.addError("name", accountMessages.nameRequired());
        } else if (dto.name().length() < 3 || dto.name().length() > 100) {
            vr.addError("name", accountMessages.nameInvalid());
        }

        if (dto.description() == null || dto.description().isBlank()
                || dto.description().length() < 10
                || dto.description().length() > 500) {
            vr.addError("description", accountMessages.descriptionInvalid());
        }


        if (dto.requester() == null || dto.requester().length() < 5) {
            vr.addError("requester", accountMessages.requesterInvalid());
        }

        if (dto.initials() == null || dto.initials().isBlank()) {
            vr.addError("initials", accountMessages.initialsRequired());
        } else if (dto.initials().length() > 5) {
            vr.addError("initials", accountMessages.initialsInvalid());
        }


        if (dto.emailGroup() == null || !EMAIL_PATTERN.matcher(dto.emailGroup()).matches()) {
            vr.addError("emailGroup", accountMessages.emailGroupInvalid());
        }


        validateApprovers(dto, vr);
    }

    private void validateApprovers(AccountDTO dto, ValidationResult vr) {
        if (dto.approvers() == null || dto.approvers().isEmpty()) {
            vr.addError("approvers", accountMessages.approversRequired());
        } else {
            int index = 0;
            for (ApproverDTO approver : dto.approvers()) {
                String path = "approvers[" + index + "]";

                if (approver.funcional() == null || approver.funcional().isBlank()) {
                    vr.addError(path + ".funcional", accountMessages.funcionalRequired());
                }

                if (approver.email() == null || !EMAIL_PATTERN.matcher(approver.email()).matches()) {
                    vr.addError(path + ".email", accountMessages.emailInvalid(approver.email()));
                }
                index++;
            }
        }
    }

    @Override
    protected void validateIntegrity(AccountDTO dto, ValidationResult vr) {
        Long id = dto.registrationIdentifier() == null ? 0L : dto.registrationIdentifier();
        if (dto.name() != null && repository.existsByNameAndDeletedAtIsNullAndIdNot(dto.name(), id)) {
            vr.addError("name", accountMessages.accountExists());
        }
    }

    @Override
    public String recordRequired() {
        return accountMessages.recordRequired();
    }

    @Override
    public String entityName() {
        return Account.class.getSimpleName();
    }

    private AccountTypeEnum getAccountTypeEnum(String name) {
        return BaseEnum.from(AccountTypeEnum.class, name);
    }
}
