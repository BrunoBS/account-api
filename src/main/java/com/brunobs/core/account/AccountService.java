package com.brunobs.core.account;

import com.brunobs.core.catalog.type.account.AccountType;
import com.brunobs.core.catalog.type.account.AccountTypeService;
import com.brunobs.core.onboarding.OnboardingService;
import com.brunobs.core.onboarding.type.OnboardingTypeEnum;
import com.brunobs.exception.ValidationException;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    private final OnboardingService onboardingService;
    private final AccountRepository repository;
    private final AccountValidator validator;
    private final AccountMapper mapper;
    private final AccountTypeService typeService;

    public AccountService(OnboardingService onboardingService, AccountRepository repository,
                          AccountValidator validator,
                          AccountMapper mapper,
                          AccountTypeService typeService) {
        this.onboardingService = onboardingService;
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.typeService = typeService;
    }

    public AccountDTO findByIdAndStatus(AccountDTO dto) {
        Account account = getAccount(dto.id(), dto.active());
        return mapper.toDTO(account);
    }

    public List<AccountDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public AccountDTO create(AccountDTO dto) {
        validator.validateForCreate(dto);

        AccountType type = typeService.findByName(dto.type());
        Account account = mapper.toEntity(dto, type);
        businessRules(account);
        account.setOnboarding(false);
        Account savedAccount = repository.save(account);
        onboardingService.registerStageCompletion(savedAccount.getId(), OnboardingTypeEnum.ACCOUNT_REGISTRATION, "USER");
        return mapper.toDTO(savedAccount);
    }


    @Transactional
    public AccountDTO update(AccountDTO dto) {
        validator.validateForUpdate(dto);

        Account account = getAccount(dto.id());
        AccountType type = typeService.findByName(dto.type());
        businessRules(account);
        mapper.updateEntity(account, dto, type);
        Account updatedAccount = repository.save(account);

        return mapper.toDTO(updatedAccount);
    }

    @Transactional
    public void delete(AccountDTO dto) {
        validator.validateForDelete(dto.id());

        Account account = getAccount(dto.id(), dto.active());
        account.setActive(false);
        repository.save(account);
    }

    public Account getAccount(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("account", BaseValidator.MSG_NOT_FOUND)));
    }

    public Account getAccount(Long id, boolean active) {
        return repository.findByIdAndActive(id, active)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("account", BaseValidator.MSG_NOT_FOUND)));
    }

    private static void businessRules(Account account) {
        if (account.getAuthorizerGroup() == null) {
            account.setAuthorizerGroup(account.getInitials());
        }
    }
}
