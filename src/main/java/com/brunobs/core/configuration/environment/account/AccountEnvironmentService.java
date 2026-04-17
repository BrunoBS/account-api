package com.brunobs.core.configuration.environment.account;


import com.brunobs.config.context.UserContext;
import com.brunobs.config.context.UserSession;
import com.brunobs.core.catalog.type.authorization.AuthorizationTypeEnum;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.PublisherProjection;
import com.brunobs.core.configuration.environment.account.dto.AccountConfigurationProjection;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentDTO;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentIdDTO;
import com.brunobs.core.environment.Environment;
import com.brunobs.core.onboarding.OnboardingService;
import com.brunobs.core.onboarding.phase.OnboardingPhaseEnum;
import com.brunobs.exception.ValidationException;
import com.brunobs.message.feature.AccountEnvMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountEnvironmentService {
    private final OnboardingService onboardingService;
    private final AccountEnvironmentRepository repository;
    private final AccountEnvironmentMapper mapper;
    private final AccountEnvironmentValidator validator;
    private final AccountEnvMessages accountEnvMessages;
    UserSession session = UserContext.get();

    public AccountEnvironmentService(OnboardingService onboardingService, AccountEnvironmentRepository repository,
                                     AccountEnvironmentMapper mapper,
                                     AccountEnvironmentValidator validator, AccountEnvMessages accountEnvMessages) {
        this.onboardingService = onboardingService;
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.accountEnvMessages = accountEnvMessages;
    }


    public EnvironmentConfigDTO findById(Long accountId, Long environmentId) {
        AccountEnvironment entity = getAccountEnvironment(accountId, environmentId);
        return mapper.toDTO(entity);
    }


    public EnvironmentConfigDTO configuration(AccountEnvironmentDTO dto) {
        validator.validateForCreate(dto);
        AccountEnvironment entity = mapper.toEntity(dto);
        repository.save(entity);
        if (isDefaultDevelopmentEnvironment(dto.environment())) {
            onboardingService.registerStageCompletion(entity.getAccountId(), OnboardingPhaseEnum.ACCOUNT_FIRST_ENVIRONMENT, session.getUserId());
        }
        return mapper.toDTO(entity);
    }


    public void delete(AccountEnvironmentIdDTO idDto) {
        validator.validateForDelete(idDto);
        repository.deleteByIdAccountIdAndIdEnvironmentId(idDto.accountId(), idDto.environmentId());
    }


    public AccountEnvironment getAccountEnvironment(Long accountId, Long environmentId) {
        return repository
                .findByIdAccountIdAndIdEnvironmentId(accountId, environmentId)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("environment", accountEnvMessages.notFound())));
    }

    public List<AccountConfigurationProjection> findConfigurationsByAccount(Long accountId, Long environmentId) {
        return repository.findConfigurationsByAccount(accountId, environmentId);
    }

    public List<PublisherProjection> findPublishersByEnvironment(Long accountId, Long environmentId) {
        return repository.findPublishersByEnvironment(accountId, environmentId);
    }

    private boolean isDefaultDevelopmentEnvironment(Environment environment) {

        EnvironmentTypeEnum type = BaseEnum.from(EnvironmentTypeEnum.class, environment.getType().getName());
        AuthorizationTypeEnum auth = BaseEnum.from(AuthorizationTypeEnum.class, environment.getAuthorizationType().getName());
        return EnvironmentTypeEnum.DEFAULT.equals(type) && AuthorizationTypeEnum.DEV.equals(auth);
    }

}

