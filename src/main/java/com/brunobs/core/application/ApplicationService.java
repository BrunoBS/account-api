package com.brunobs.core.application;

import com.brunobs.core.account.Account;
import com.brunobs.core.account.AccountService;
import com.brunobs.core.catalog.type.account.AccountTypeEnum;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeType;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeService;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureType;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureTypeService;
import com.brunobs.core.catalog.type.language.LanguageType;
import com.brunobs.core.catalog.type.language.LanguageTypeService;
import com.brunobs.exception.ValidationException;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.validation.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;
    private final ApplicationValidator validator;
    private final ApplicationMapper mapper;
    private final AccountService accountService;
    private final LanguageTypeService languageService;
    private final ApplicationScopeTypeService scopeService;
    private final InfrastructureTypeService infrastructureService;

    public ApplicationService(ApplicationRepository repository,
                              ApplicationValidator validator,
                              ApplicationMapper mapper,
                              AccountService accountService,
                              LanguageTypeService languageService,
                              ApplicationScopeTypeService scopeService,
                              InfrastructureTypeService infrastructureService) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.accountService = accountService;
        this.languageService = languageService;
        this.scopeService = scopeService;
        this.infrastructureService = infrastructureService;
    }

    public ApplicationDTO findById(ApplicationDTO dto) {
        Application entity = getApplication(dto.id(), dto.active());
        return mapper.toDTO(entity);
    }

    public List<ApplicationDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public ApplicationDTO create(ApplicationDTO dto) {
        validator.validateForCreate(dto);

        ApplicationDependencies deps = resolveDependencies(dto);
        Application entity = mapper.toEntity(dto, deps.account(), deps.language(), deps.scope(), deps.infrastructure());
        businessRules(entity, deps.account);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public ApplicationDTO update(ApplicationDTO dto) {
        validator.validateForUpdate(dto);

        Application entity = getApplication(dto.id());
        ApplicationDependencies deps = resolveDependencies(dto);
        mapper.updateEntity(entity, dto, deps.account(), deps.language(), deps.scope(), deps.infrastructure());
        businessRules(entity, deps.account);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void delete(ApplicationDTO dto) {
        validator.validateForDelete(dto.id());
        Application entity = getApplication(dto.id(), dto.active());
        entity.setActive(false);
        repository.save(entity);
    }

    public Application getApplication(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("application", BaseValidator.MSG_NOT_FOUND)));
    }


    public Application getApplication(Long id, boolean active) {
        return repository.findByIdAndActive(id, active)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("application", BaseValidator.MSG_NOT_FOUND)));
    }

    public Application getApplication(Long accountId, Long applicationId, boolean active) {
        return repository.findByIdAndAccountIdAndActive(applicationId, accountId, active)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("application", BaseValidator.MSG_NOT_FOUND)));
    }


    private ApplicationDependencies resolveDependencies(ApplicationDTO dto) {
        Account account = accountService.getAccount(dto.accountId());
        LanguageType language = languageService.findByName(dto.languageType());
        ApplicationScopeType scope = scopeService.findByName(dto.applicationScopeType());
        InfrastructureType infrastructure = infrastructureService.findByName(dto.infrastructureType());


        AccountTypeEnum accountType = BaseEnum.from(AccountTypeEnum.class, account.getType().getName());
        if (AccountTypeEnum.ADMIN.equals(accountType)) {
            throw new ValidationException(
                    new ValidationResult("account", ApplicationValidator.MSG_ACCOUNT_REQUIRED));
        }

        return new ApplicationDependencies(account, language, scope, infrastructure);
    }

    private static void businessRules(Application application, Account account) {
        if (application.getAuthorizerGroup() == null) {
            application.setAuthorizerGroup(account.getInitials());
        }
    }

    private record ApplicationDependencies(
            Account account,
            LanguageType language,
            ApplicationScopeType scope,
            InfrastructureType infrastructure
    ) {
    }
}
