package com.brunobs.core.account;

import com.brunobs.core.account.repository.AccountRepository;
import com.brunobs.core.catalog.type.account.AccountType;
import com.brunobs.core.catalog.type.account.AccountTypeService;
import com.brunobs.core.onboarding.OnboardingService;
import com.brunobs.core.onboarding.phase.OnboardingPhaseEnum;
import com.brunobs.exception.ValidationException;
import com.brunobs.proxy.ProxyAuthorizer;
import com.brunobs.message.feature.AccountMessages;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {


    private final OnboardingService onboardingService;
    private final AccountRepository repository;
    private final AccountValidator validator;
    private final AccountMapper mapper;
    private final AccountTypeService typeService;
    private final AccountMessages accountMessage;

    public AccountService(OnboardingService onboardingService,
                          AccountRepository repository,
                          AccountValidator validator,
                          AccountMapper mapper,
                          AccountTypeService typeService, AccountMessages accountMessage) {
        this.onboardingService = onboardingService;
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.typeService = typeService;
        this.accountMessage = accountMessage;
    }

    @ProxyAuthorizer
    public AccountDTO findById(Long id) {
        Account account = getAccount(id);
        return mapper.toDTO(account);
    }


    @ProxyAuthorizer
    public List<?> findAll(Boolean active, Boolean simplify, String typeName, String tagName) {
        active = (active == null ? Boolean.FALSE : active);
        simplify = (simplify == null ? Boolean.FALSE : simplify);
        if (simplify) {
            return repository.findAllSummaries(active, typeName, tagName);
        }
        return repository.findAllFull(active, typeName, tagName).stream().map(mapper::toDTO).toList();
    }

    @Transactional
    public AccountDTO create(AccountDTO dto) {
        validator.validateForCreate(dto);
        LocalDateTime now = LocalDateTime.now();
        AccountType type = typeService.findByName(dto.accountType());
        Account account = mapper.toEntity(dto, type);
        businessRules(account);
        account.setOnboarding(false);
        account.setCreatedAt(now);
        account.setUpdatedAt(now);
        Account savedAccount = repository.save(account);
        onboardingService.registerStageCompletion(savedAccount.getId(), OnboardingPhaseEnum.ACCOUNT_REGISTRATION, "USER");
        return mapper.toDTO(savedAccount);
    }


    @Transactional
    public AccountDTO update(AccountDTO dto) {
        validator.validateForUpdate(dto);

        Account account = getAccount(dto.id());
        AccountType type = typeService.findByName(dto.accountType());
        mapper.updateEntity(account, dto, type);
        businessRules(account);
        account.setUpdatedAt(LocalDateTime.now());
        Account updatedAccount = repository.save(account);

        return mapper.toDTO(updatedAccount);
    }

    @Transactional
    public void delete(Long id) {
        validator.validateForDelete(id);

        Account entity = getAccount(id);
        LocalDateTime now = LocalDateTime.now();
        entity.setDeletedAt(now);
        entity.setUpdatedAt(now);
        repository.save(entity);
    }

    @Transactional
    public AccountDTO restore(Long id, String newName) {
        Account entity = repository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), accountMessage.restoreInvalid())));

        String finalName = newName != null ? newName : entity.getName();
        boolean exists = repository.existsByNameAndDeletedAtIsNull(finalName);
        if (exists) {
            throw new ValidationException(
                    new ValidationResult(validator.entityName(), accountMessage.nameDuplicated(finalName)));
        }

        entity.setName(finalName);
        entity.setDeletedAt(null);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toDTO(repository.save(entity));
    }

    public Account getAccount(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), accountMessage.notFound())));
    }

    public Account getAccount(String nome) {
        return repository.findByNameAndDeletedAtIsNull(nome)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult(validator.entityName(), accountMessage.notFound())));
    }

    private static void businessRules(Account account) {
            String suffix = account.getAuthorizerGroup();
        boolean isEmpty = suffix == null || suffix.isBlank();
        account.setAuthorizerGroup(isEmpty ? account.getAcronym() : account.getAcronym() + "-" + suffix);
    }
}
