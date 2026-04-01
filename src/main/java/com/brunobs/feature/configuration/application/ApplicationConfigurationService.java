package com.brunobs.feature.configuration.application;


import com.brunobs.core.application.Application;
import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.configuration.PublisherProjection;
import com.brunobs.core.configuration.environment.application.ApplicationEnvironmentService;
import com.brunobs.core.configuration.environment.application.dto.ApplicationConfigurationProjection;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentDTO;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentIdDTO;
import com.brunobs.core.environment.Environment;
import com.brunobs.core.publisher.Publisher;
import com.brunobs.exception.ValidationException;
import com.brunobs.feature.EntityValidationService;
import com.brunobs.feature.configuration.application.dto.ApplicationEnvironmentPublishersResponseDTO;
import com.brunobs.shared.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationConfigurationService {


    private final EntityValidationService entityValidationService;

    private final ApplicationEnvironmentService applicationEnvironmentService;
    private final SchemaValidator schemaValidator;

    public ApplicationConfigurationService(EntityValidationService entityValidationService, ApplicationEnvironmentService applicationEnvironmentService, SchemaValidator schemaValidator) {
        this.entityValidationService = entityValidationService;


        this.applicationEnvironmentService = applicationEnvironmentService;
        this.schemaValidator = schemaValidator;
    }

    @Transactional
    public EnvironmentConfigDTO create(Long accountId, Long applicationId, EnvironmentConfigDTO dto) {
        ApplicationEnvironmentDTO applicationEnvironmentDTO = resolveApplicationEnvironmentDTO(accountId, applicationId, dto);
        return applicationEnvironmentService.create(applicationEnvironmentDTO);
    }

    @Transactional
    public void delete(Long accountId, Long applicationId, Long environmentId) {
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        Environment environment = entityValidationService.validateEnvironment(environmentId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);
        ApplicationEnvironmentIdDTO idDto = new ApplicationEnvironmentIdDTO(environment.getId(), application.getId());
        applicationEnvironmentService.delete(idDto);
    }

    public List<ApplicationConfigurationProjection> findByApplication(Long accountId, Long applicationId) {
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);

        return applicationEnvironmentService.findConfigurationsByAplication(application.getId(), null);
    }

    public ApplicationConfigurationProjection findByApplicationAndEnvironment(Long accountId, Long applicationId, Long environmentId) {
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        Environment environment = entityValidationService.validateEnvironment(environmentId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);

        return applicationEnvironmentService.findConfigurationsByAplication(application.getId(), environment.getId()).stream().findFirst().orElse(null);
    }

    @Transactional
    public EnvironmentConfigDTO configuration(Long accountId, Long applicationId, Long environmentId, EnvironmentConfigDTO dto) {
        EnvironmentConfigDTO configDto = dto.withEnvironmentId(environmentId);
        ApplicationEnvironmentDTO accountEnvDto = resolveApplicationEnvironmentDTO(accountId, applicationId, configDto);
        return applicationEnvironmentService.configuration(accountEnvDto);
    }

    public ApplicationEnvironmentPublishersResponseDTO findPublishersByEnvironment(Long accountId, Long applicationId, Long environmentId) {
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        Environment environment = entityValidationService.validateEnvironment(environmentId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);

        List<PublisherProjection> publishers = applicationEnvironmentService.findPublishersByEnvironment(application.getId(), environment.getId());

        return new ApplicationEnvironmentPublishersResponseDTO(application.getId(), application.getName(), environment.getId(), environment.getName(), publishers);
    }

    private ApplicationEnvironmentDTO resolveApplicationEnvironmentDTO(Long accountId, Long applicationId, EnvironmentConfigDTO dto) {
        ValidationResult vr = new ValidationResult();

        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        Environment environment = entityValidationService.validateEnvironment(dto.environmentId(), vr);

        List<PublisherConfig> publisherConfigs = dto.publishers().stream().map(p -> {
            Publisher publisher = entityValidationService.validatePublisher(p.name(), vr);
            return new PublisherConfig(publisher, p.order(), schemaValidator.toJsonString(p.parameters()));
        }).collect(Collectors.toList());

        if (vr.hasErrors()) throw new ValidationException(vr);

        return new ApplicationEnvironmentDTO(application, environment, publisherConfigs, dto.settings());
    }


}
