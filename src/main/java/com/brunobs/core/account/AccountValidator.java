package com.brunobs.core.account;

import com.brunobs.core.catalog.type.account.AccountTypeEnum;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.brunobs.core.catalog.common.BaseTypeValidator.MSG_INVALID_NAME;

@Component
public class AccountValidator extends BaseValidator<AccountDTO, Long> {


    public static final String MSG_NAME_REQUIRED = "validator.account.name.required";
    public static final String MSG_NAME_DUPLICATED = "validator.account.name.duplicated";
    public static final String MSG_NAME_INVALID = "validator.account.name.invalid";
    public static final String MSG_DESC_INVALID = "validator.account.description.invalid";
    public static final String MSG_REQUESTER_INVALID = "validator.account.requester.invalid";
    public static final String MSG_INITIALS_REQUIRED = "validator.account.initials.required";
    public static final String MSG_INITIALS_INVALID = "validator.account.initials.invalid";
    public static final String MSG_EMAIL_GROUP_INVALID = "validator.account.email.group.invalid";
    public static final String MSG_APPROVERS_REQUIRED = "validator.account.approvers.required";
    public static final String MSG_FUNCIONAL_REQUIRED = "validator.account.funcional.required";
    public static final String MSG_EMAIL_INVALID = "validator.account.email.invalid";
    public static final String MSG_RESTORE_ACCOUNT_ACTIVE = "validator.account.restore.account.active";

    private final AccountRepository repository;

    public AccountValidator(AccountRepository repository, MessageSource messageSource) {
        super(messageSource);
        this.repository = repository;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");

    @Override
    public void validateAttributes(AccountDTO dto, ValidationResult vr) {
        AccountTypeEnum typeEnum = getAccountTypeEnum(dto.accountType());

        // Account Type Validation
        if (typeEnum == null || AccountTypeEnum.CATALOG.equals(typeEnum)) {
            List<String> opcoesValidas = Arrays.asList(AccountTypeEnum.ADMIN.name(), AccountTypeEnum.MANAGER.name());
            vr.addError("accountType",
                    getMessage(MSG_INVALID_NAME, "accountType",
                            BaseEnum.getOptionsValid(opcoesValidas, AccountTypeEnum.class, messageSource)));

        }

        if (dto.name() == null || dto.name().isBlank()) {
            vr.addError("name", getMessage(MSG_NAME_REQUIRED));
        } else if (dto.name().length() < 3 || dto.name().length() > 100) {
            vr.addError("name", getMessage(MSG_NAME_INVALID));
        }

        if (dto.description() == null || dto.description().isBlank()
                || dto.description().length() < 10
                || dto.description().length() > 500) {
            vr.addError("description", getMessage(MSG_DESC_INVALID));
        }


        if (dto.requester() == null || dto.requester().length() < 5) {
            vr.addError("requester", getMessage(MSG_REQUESTER_INVALID));
        }

        if (dto.initials() == null || dto.initials().isBlank()) {
            vr.addError("initials", getMessage(MSG_INITIALS_REQUIRED));
        } else if (dto.initials().length() > 5) {
            vr.addError("initials", getMessage(MSG_INITIALS_INVALID));
        }


        if (dto.emailGroup() == null || !EMAIL_PATTERN.matcher(dto.emailGroup()).matches()) {
            vr.addError("emailGroup", getMessage(MSG_EMAIL_GROUP_INVALID));
        }


        validateApprovers(dto, vr);
    }

    private void validateApprovers(AccountDTO dto, ValidationResult vr) {
        if (dto.approvers() == null || dto.approvers().isEmpty()) {
            vr.addError("approvers", getMessage(MSG_APPROVERS_REQUIRED));
        } else {
            int index = 0;
            for (ApproverDTO approver : dto.approvers()) {
                String path = "approvers[" + index + "]";

                if (approver.funcional() == null || approver.funcional().isBlank()) {
                    vr.addError(path + ".employeeId", getMessage(MSG_FUNCIONAL_REQUIRED));
                }

                if (approver.email() == null || !EMAIL_PATTERN.matcher(approver.email()).matches()) {
                    vr.addError(path + ".email", getMessage(MSG_EMAIL_INVALID));
                }
                index++;
            }
        }
    }

    @Override
    protected void validateIntegrity(AccountDTO dto, ValidationResult vr) {
        Long id = dto.registrationIdentifier() == null ? 0L : dto.registrationIdentifier();
        if (dto.name() != null && repository.existsByNameAndDeletedAtIsNullAndIdNot(dto.name(), id)) {
            vr.addError("name", getMessage(MSG_NAME_DUPLICATED));
        }
    }

    @Override
    protected boolean recordExists(Long id) {
        return repository.existsByIdAndDeletedAtIsNull(id);
    }

    @Override
    public String entityName() {
        return Account.class.getSimpleName();
    }

    private AccountTypeEnum getAccountTypeEnum(String name) {
        return BaseEnum.from(AccountTypeEnum.class, name);
    }
}
