package com.brunobs.core.configuration.environment.application;


import com.brunobs.core.catalog.type.authorization.AuthorizationTypeEnum;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.PublisherProjection;
import com.brunobs.core.configuration.environment.application.dto.ApplicationConfigurationProjection;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentDTO;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentIdDTO;
import com.brunobs.core.environment.Environment;
import com.brunobs.core.onboarding.OnboardingService;
import com.brunobs.core.onboarding.phase.OnboardingPhaseEnum;
import com.brunobs.exception.ValidationException;
import com.brunobs.message.feature.ApplicationEnvMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicationEnvironmentService {
    private final OnboardingService onboardingService;
    private final ApplicationEnvironmentRepository repository;
    private final ApplicationEnvironmentMapper mapper;
    private final ApplicationEnvironmentValidator validator;
    private final ApplicationEnvMessages applicationEnvMessages;

    public ApplicationEnvironmentService(OnboardingService onboardingService,
                                         ApplicationEnvironmentRepository repository,
                                         ApplicationEnvironmentMapper mapper,
                                         ApplicationEnvironmentValidator validator, ApplicationEnvMessages applicationEnvMessages) {
        this.onboardingService = onboardingService;
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.applicationEnvMessages = applicationEnvMessages;

    }


    public EnvironmentConfigDTO findById(Long accountId, Long environmentId) {
        ApplicationEnvironment entity = getApplicationEnvironment(accountId, environmentId);
        return mapper.toDTO(entity);
    }


    public EnvironmentConfigDTO create(ApplicationEnvironmentDTO dto) {
        validator.validateForCreate(dto);
        ApplicationEnvironment entity = mapper.toEntity(dto);
        repository.save(entity);
        if (isDefaultDevelopmentEnvironment(dto.environment())) {
            onboardingService.registerStageCompletion(entity.getApplicationId(), OnboardingPhaseEnum.ACCOUNT_FIRST_ENVIRONMENT, "USER");
        }
        return mapper.toDTO(entity);
    }


    public EnvironmentConfigDTO configuration(ApplicationEnvironmentDTO dto) {
        validator.validateForUpdate(dto);
        ApplicationEnvironment entity = getApplicationEnvironment(dto.getApplicationId(), dto.getEnvironmentId());
        mapper.updateEntity(entity, dto);
        repository.save(entity);
        return mapper.toDTO(entity);
    }


    public void delete(ApplicationEnvironmentIdDTO idDto) {
        validator.validateForDelete(idDto);
        repository.deleteByIdApplicationIdAndIdEnvironmentId(idDto.applicationId(), idDto.environmentId());
    }


    public ApplicationEnvironment getApplicationEnvironment(Long accountId, Long environmentId) {
        return repository
                .findByIdApplicationIdAndIdEnvironmentId(accountId, environmentId)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("environment", applicationEnvMessages.notFound())));
    }

    public List<ApplicationConfigurationProjection> findConfigurationsByAplication(Long accountId, Long environmentId) {
        return repository.findConfigurationsByApplication(accountId, environmentId);
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

