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
import com.brunobs.core.onboarding.type.OnboardingTypeEnum;
import com.brunobs.exception.ValidationException;
import com.brunobs.shared.BaseEnum;
import com.brunobs.shared.validation.BaseValidator;
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

    public ApplicationEnvironmentService(OnboardingService onboardingService, ApplicationEnvironmentRepository repository,
                                         ApplicationEnvironmentMapper mapper,
                                         ApplicationEnvironmentValidator validator) {
        this.onboardingService = onboardingService;
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<ApplicationEnvironment> getApplicationConfigurations(Long sourceEnvironmentId) {
        return repository.findByIdEnvironmentId(sourceEnvironmentId);
    }

    public List<EnvironmentConfigDTO> listByApplication(Long applicationId) {
        return repository.findByIdApplicationId(applicationId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public EnvironmentConfigDTO findByIds(Long applicationId, Long environmentId) {
        ApplicationEnvironment entity = getApplicationEnvironment(applicationId, environmentId);
        return mapper.toDTO(entity);
    }

    public EnvironmentConfigDTO create(ApplicationEnvironmentDTO dto) {
        validator.validateForCreate(dto);
        ApplicationEnvironment entity = mapper.toEntity(dto);
        repository.save(entity);
        if (isDefaultDevelopmentEnvironment(dto.environment())) {
            onboardingService.registerStageCompletion(dto.application().getAccount().getId(),
                    OnboardingTypeEnum.APPLICATION_FIRST_ENVIRONMENT, "USER");
        }
        return mapper.toDTO(entity);
    }


    public EnvironmentConfigDTO update(ApplicationEnvironmentDTO dto) {
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

    public ApplicationEnvironment getApplicationEnvironment(Long applicationId, Long environmentId) {
        return repository
                .findByIdApplicationIdAndIdEnvironmentId(applicationId, environmentId)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("environment", BaseValidator.MSG_NOT_FOUND)));
    }

    public List<ApplicationConfigurationProjection> findByApplicationAndEnvironment(Long applicationId, Long environmentId) {
        return repository.findConfigurationsByApplication(applicationId, environmentId);
    }

    public List<PublisherProjection> findPublishersByEnvironment(Long applicationId, Long environmentId) {
        return repository.findPublishersByEnvironment(applicationId, environmentId);
    }

    private boolean isDefaultDevelopmentEnvironment(Environment environment) {

        EnvironmentTypeEnum type = BaseEnum.from(EnvironmentTypeEnum.class, environment.getType().getName());
        AuthorizationTypeEnum auth = BaseEnum.from(AuthorizationTypeEnum.class, environment.getAuthorizationType().getName());
        return EnvironmentTypeEnum.DEFAULT.equals(type) && AuthorizationTypeEnum.DEV.equals(auth);
    }
}

