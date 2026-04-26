package com.brunobs.core.configuration.environment.application;


import com.brunobs.core.catalog.type.authorization.AuthorizationTypeEnum;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.configuration.PublisherProjection;
import com.brunobs.core.configuration.environment.account.AccountEnvironment;
import com.brunobs.core.configuration.environment.account.AccountEnvironmentService;
import com.brunobs.core.configuration.environment.application.dto.ApplicationConfigurationProjection;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentDTO;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentIdDTO;
import com.brunobs.core.environment.Environment;
import com.brunobs.core.onboarding.OnboardingService;
import com.brunobs.core.onboarding.phase.OnboardingPhaseEnum;
import com.brunobs.exception.ValidationException;
import com.brunobs.message.feature.ApplicationEnvMessages;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicationEnvironmentService {
    private final OnboardingService onboardingService;
    private final AccountEnvironmentService accountEnvironmentService;
    private final ApplicationEnvironmentRepository repository;
    private final ApplicationEnvironmentMapper mapper;
    private final ApplicationEnvironmentValidator validator;
    private final ApplicationEnvMessages applicationEnvMessages;
    private final SchemaValidator schemaValidator;

    public ApplicationEnvironmentService(OnboardingService onboardingService,
                                         AccountEnvironmentService accountEnvironmentService,
                                         ApplicationEnvironmentRepository repository,
                                         ApplicationEnvironmentMapper mapper,
                                         ApplicationEnvironmentValidator validator,
                                         ApplicationEnvMessages applicationEnvMessages, SchemaValidator schemaValidator) {
        this.onboardingService = onboardingService;
        this.accountEnvironmentService = accountEnvironmentService;
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.applicationEnvMessages = applicationEnvMessages;
        this.schemaValidator = schemaValidator;
    }


    public EnvironmentConfigDTO findById(Long accountId, Long environmentId) {
        ApplicationEnvironment entity = getApplicationEnvironment(accountId, environmentId);
        return mapper.toDTO(entity);
    }


    public EnvironmentConfigDTO configuration(ApplicationEnvironmentDTO dto, Boolean cloneSettingsAccount) {

        ApplicationEnvironment entity = repository
                .findByIdApplicationIdAndIdEnvironmentId(dto.getApplicationId(), dto.getEnvironmentId()).orElse(
                        mapper.toEntity(dto)
                );

        if (Boolean.TRUE.equals(cloneSettingsAccount)) {
            AccountEnvironment accountEnv = accountEnvironmentService.getAccountEnvironment(
                    dto.application().getAccount().getId(),
                    dto.environment().getId()
            );

            List<PublisherConfig> clones = accountEnv.getPublishers().stream()
                    .map(this::clonePublisher)
                    .toList();

            dto = new ApplicationEnvironmentDTO(
                    dto.application(),
                    dto.environment(),
                    clones,
                    dto.settings()
            );
        }

        validator.validateForUpdate(dto);
        entity.getPublishers().clear();
        mapper.updateEntity(entity, dto);
        return mapper.toDTO(repository.save(entity));
    }



    // Método auxiliar para manter o código limpo
    private PublisherConfig clonePublisher(PublisherConfig source) {
        PublisherConfig target = new PublisherConfig();
        target.setOrder(source.getOrder());
        target.setParameters(source.getParameters());
        target.setPublisher(source.getPublisher());
        return target;
    }



    public void delete(ApplicationEnvironmentIdDTO idDto) {
        validator.validateForDelete(idDto);
        repository.deleteByIdApplicationIdAndIdEnvironmentId(idDto.applicationId(), idDto.environmentId());
    }


    public ApplicationEnvironment getApplicationEnvironment(Long applicationId, Long environmentId) {
        return repository
                .findByIdApplicationIdAndIdEnvironmentId(applicationId, environmentId)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("environment", applicationEnvMessages.notFound())));
    }

    public List<ApplicationConfigurationProjection> findConfigurationsByAplication(Long accountId, Long applicationId, Long environmentId) {
        return repository.findConfigurationsByApplication(accountId, applicationId, environmentId);
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

