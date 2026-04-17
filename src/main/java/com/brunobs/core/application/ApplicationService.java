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
import com.brunobs.message.feature.ApplicationMessages;
import com.brunobs.proxy.ProxyAuthorizer;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final MessageSource messageSource;
    private final ApplicationMessages applicationMessages;

    public ApplicationService(ApplicationRepository repository,
                              ApplicationValidator validator,
                              ApplicationMapper mapper,
                              AccountService accountService,
                              LanguageTypeService languageService,
                              ApplicationScopeTypeService scopeService,
                              InfrastructureTypeService infrastructureService, MessageSource messageSource, ApplicationMessages applicationMessages) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.accountService = accountService;
        this.languageService = languageService;
        this.scopeService = scopeService;
        this.infrastructureService = infrastructureService;
        this.messageSource = messageSource;
        this.applicationMessages = applicationMessages;
    }

    public ApplicationDTO findById(ApplicationDTO dto) {
        Application entity = getApplication(dto.accountId(), dto.id());
        return mapper.toDTO(entity);
    }

    @ProxyAuthorizer
    public List<ApplicationDTO> findByAccountIdAndActive(ApplicationDTO dto, boolean active) {
        List<Application> lista =
                active ?
                        repository.findByAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNull(dto.accountId()) :
                        repository.findByAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNotNull(dto.accountId());
        return lista.stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public ApplicationDTO create(ApplicationDTO dto) {
        validator.validateForCreate(dto);
        LocalDateTime now = LocalDateTime.now();
        ApplicationDependencies deps = resolveDependencies(dto);
        Application entity = mapper.toEntity(dto, deps.account(), deps.language(), deps.scope(), deps.infrastructure());
        businessRules(entity, deps.account);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public ApplicationDTO update(ApplicationDTO dto) {
        validator.validateForUpdate(dto);

        Application entity = getApplication(dto.accountId(), dto.id());
        ApplicationDependencies deps = resolveDependencies(dto);
        mapper.updateEntity(entity, dto, deps.account(), deps.language(), deps.scope(), deps.infrastructure());
        businessRules(entity, deps.account);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void delete(ApplicationDTO dto) {
        validator.validateForDelete(dto.id());
        Application entity = getApplication(dto.accountId(), dto.id());
        LocalDateTime now = LocalDateTime.now();
        entity.setDeletedAt(now);
        entity.setUpdatedAt(now);


        repository.save(entity);
    }

    @Transactional
    public ApplicationDTO restore(Long accountId, Long id, String newName) {
        Application entity = repository.findByIdAndAccountIdAndDeletedAtIsNotNullAndAccountDeletedAtIsNull(id, accountId)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("application", applicationMessages.notFound())));

        String finalName = newName != null ? newName : entity.getName();
        boolean exists = repository.existsByNameAndDeletedAtIsNullAndAccountId(finalName, accountId);
        if (exists) {
            throw new ValidationException(
                    new ValidationResult(validator.entityName(), applicationMessages.nameDuplicated(finalName)));
        }
        entity.setName(finalName);
        entity.setDeletedAt(null);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toDTO(repository.save(entity));

    }


    public Application getApplication(String nameApplicatione, Long accountId) {
        return repository.findByNameAndAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNull(nameApplicatione, accountId)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("application", applicationMessages.notFound())));
    }

    public Application getApplication(Long accountId, Long applicationId) {
        return repository.findByIdAndAccountIdAndDeletedAtIsNullAndAccountDeletedAtIsNull(applicationId, accountId)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("application", applicationMessages.notFound())));
    }


    private ApplicationDependencies resolveDependencies(ApplicationDTO dto) {
        Account account = accountService.getAccount(dto.accountId());
        LanguageType language = languageService.findByName(dto.languageType());
        ApplicationScopeType scope = scopeService.findByName(dto.applicationScopeType());
        InfrastructureType infrastructure = infrastructureService.findByName(dto.infrastructureType());


        AccountTypeEnum accountType = BaseEnum.from(AccountTypeEnum.class, account.getAccountType().getName());
        if (!AccountTypeEnum.MANAGER.equals(accountType)) {
            String currentAccountType = accountType == null ? "null" : accountType.name();
            throw new ValidationException(
                    new ValidationResult("applicatins",
                            applicationMessages.accountTypeUnauthorized(currentAccountType)));
        }

        return new ApplicationDependencies(account, language, scope, infrastructure);
    }

    private static void businessRules(Application application, Account account) {
        if (application.getAuthorizerGroup() == null) {
            application.setAuthorizerGroup(account.getAcronym());
        }
    }

    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    private record ApplicationDependencies(
            Account account,
            LanguageType language,
            ApplicationScopeType scope,
            InfrastructureType infrastructure
    ) {
    }
}
