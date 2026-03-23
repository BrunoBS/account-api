package com.brunobs.core.account;

import com.brunobs.core.catalog.type.account.AccountType;
import com.brunobs.core.catalog.type.account.AccountTypeService;
import com.brunobs.core.onboarding.OnboardingService;
import com.brunobs.core.onboarding.phase.OnboardingPhaseEnum;
import com.brunobs.exception.ValidationException;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    protected final MessageSource messageSource;

    private final OnboardingService onboardingService;
    private final AccountRepository repository;
    private final AccountValidator validator;
    private final AccountMapper mapper;
    private final AccountTypeService typeService;

    public AccountService(MessageSource messageSource, OnboardingService onboardingService,
                          AccountRepository repository,
                          AccountValidator validator,
                          AccountMapper mapper,
                          AccountTypeService typeService) {
        this.messageSource = messageSource;
        this.onboardingService = onboardingService;
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.typeService = typeService;
    }

    public AccountDTO findById(Long id) {
        Account account = getAccount(id);
        return mapper.toDTO(account);
    }

    public List<AccountDTO> findAll(boolean active) {
        List<Account> lista =
                active ? repository.findByDeletedAtIsNull() : repository.findByDeletedAtIsNotNull();
        return lista.stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public AccountDTO create(AccountDTO dto) {
        validator.validateForCreate(dto);

        AccountType type = typeService.findByName(dto.accountType());
        Account account = mapper.toEntity(dto, type);
        businessRules(account);
        account.setOnboarding(false);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        Account savedAccount = repository.save(account);
        onboardingService.registerStageCompletion(savedAccount.getId(), OnboardingPhaseEnum.ACCOUNT_REGISTRATION, "USER");
        return mapper.toDTO(savedAccount);
    }


    @Transactional
    public AccountDTO update(AccountDTO dto) {
        validator.validateForUpdate(dto);

        Account account = getAccount(dto.id());
        AccountType type = typeService.findByName(dto.accountType());
        businessRules(account);
        mapper.updateEntity(account, dto, type);
        account.setUpdatedAt(LocalDateTime.now());
        Account updatedAccount = repository.save(account);

        return mapper.toDTO(updatedAccount);
    }

    @Transactional
    public void delete(Long id) {
        validator.validateForDelete(id);

        Account account = getAccount(id);
        account.setDeletedAt(LocalDateTime.now());
        repository.save(account);
    }

    @Transactional
    public AccountDTO restore(Long id, String newName) {
        Account account = repository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), getMessage(AccountValidator.MSG_RESTORE_ACCOUNT_ACTIVE))));

        String finalName = newName != null ? newName : account.getName();
        boolean exists = repository.existsByNameAndDeletedAtIsNull(finalName);
        if (exists) {
            throw new ValidationException(
                    new ValidationResult(validator.entityName(), getMessage(AccountValidator.MSG_NAME_DUPLICATED)));
        }

        account.setName(finalName);
        account.setDeletedAt(null);
        return mapper.toDTO(repository.save(account));
    }

    public Account getAccount(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), getMessage(BaseValidator.MSG_NOT_FOUND))));
    }

    public Account getAccount(String nome) {
        return repository.findByNameAndDeletedAtIsNull(nome)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), getMessage(BaseValidator.MSG_NOT_FOUND))));
    }

    private static void businessRules(Account account) {
        if (account.getAuthorizerGroup() == null) {
            account.setAuthorizerGroup(account.getInitials());
        }
    }

    protected String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
