package com.brunobs.features.configuration.application;


import com.brunobs.core.application.Application;
import com.brunobs.core.application.ApplicationService;
import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.PublisherConfig;
import com.brunobs.core.configuration.PublisherProjection;
import com.brunobs.core.configuration.environment.application.ApplicationEnvironmentService;
import com.brunobs.core.configuration.environment.application.dto.ApplicationConfigurationProjection;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentDTO;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentIdDTO;
import com.brunobs.core.environment.Environment;
import com.brunobs.core.environment.EnvironmentService;
import com.brunobs.core.publisher.Publisher;
import com.brunobs.core.publisher.PublisherService;
import com.brunobs.exception.ValidationException;
import com.brunobs.features.configuration.application.dto.ApplicationEnvironmentPublishersResponseDTO;
import com.brunobs.shared.validation.SchemaValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationConfigurationService {


    private final ApplicationService applicationService;
    private final EnvironmentService environmentService;
    private final PublisherService publisherService;
    private final ApplicationEnvironmentService applicationEnvironmentService;
    private final SchemaValidator schemaValidator;

    public ApplicationConfigurationService(ApplicationService applicationService,
                                           EnvironmentService environmentService,
                                           PublisherService publisherService,
                                           ApplicationEnvironmentService applicationEnvironmentService,
                                           SchemaValidator schemaValidator
    ) {


        this.applicationService = applicationService;
        this.environmentService = environmentService;
        this.publisherService = publisherService;
        this.applicationEnvironmentService = applicationEnvironmentService;
        this.schemaValidator = schemaValidator;
    }

    @Transactional
    public EnvironmentConfigDTO create(Long accountId, Long applicationId, EnvironmentConfigDTO dto) {
        ApplicationEnvironmentDTO applicationEnvironmentDTO = resolveApplicationEnvironmentDTO(accountId, applicationId, dto);
        return applicationEnvironmentService.create(applicationEnvironmentDTO);
    }

    @Transactional
    public void delete(Long applicationId, Long environmentId) {
        ApplicationEnvironmentIdDTO idDto = new ApplicationEnvironmentIdDTO(environmentId, applicationId);
        applicationEnvironmentService.delete(idDto);
    }

    public List<ApplicationConfigurationProjection> findByApplication(Long accountId, Long applicationId) {
        ValidationResult vr = new ValidationResult();
        Application application = validateApplication(accountId, applicationId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);

        return applicationEnvironmentService.findConfigurationsByApplication(application.getId(), null);
    }

    public ApplicationConfigurationProjection findByAccountAndEnvironment(Long accountId, Long applicationId, Long environmentId) {
        ValidationResult vr = new ValidationResult();
        Application application = validateApplication(accountId, applicationId, vr);
        Environment environment = validateEnvironment(environmentId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);

        return applicationEnvironmentService.findConfigurationsByApplication(application.getId(), environment.getId())
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public EnvironmentConfigDTO update(Long accountId, Long applicationId, Long environmentId, EnvironmentConfigDTO dto) {
        EnvironmentConfigDTO configDto = dto.withEnvironmentId(environmentId);
        ApplicationEnvironmentDTO accountEnvDto = resolveApplicationEnvironmentDTO(accountId, applicationId, configDto);
        return applicationEnvironmentService.update(accountEnvDto);
    }

    public ApplicationEnvironmentPublishersResponseDTO findPublishersByEnvironment(Long accountId, Long applicationId, Long environmentId) {
        ValidationResult vr = new ValidationResult();
        Application application = validateApplication(accountId, applicationId, vr);
        Environment environment = validateEnvironment(environmentId, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);

        List<PublisherProjection> publishers = applicationEnvironmentService.findPublishersByEnvironment(application.getId(), environment.getId());

        return new ApplicationEnvironmentPublishersResponseDTO(
                application.getId(), application.getName(),
                environment.getId(), environment.getName(),
                publishers
        );
    }

    private ApplicationEnvironmentDTO resolveApplicationEnvironmentDTO(Long accountId, Long applicationId, EnvironmentConfigDTO dto) {
        ValidationResult vr = new ValidationResult();

        Application application = validateApplication(accountId, applicationId, vr);
        Environment environment = validateEnvironment(dto.environmentId(), vr);

        List<PublisherConfig> publisherConfigs = dto.publishers().stream().map(p -> {
            Publisher publisher = validatePublisher(p.name(), vr);
            return new PublisherConfig(
                    publisher,
                    p.order(),
                    schemaValidator.toJsonString(p.parameters())
            );
        }).collect(Collectors.toList());

        if (vr.hasErrors()) throw new ValidationException(vr);

        return new ApplicationEnvironmentDTO(
                application,
                environment,
                publisherConfigs,
                dto.authorizerGroup()
        );
    }


    private Application validateApplication(Long accountId, Long applicationId, ValidationResult vr) {
        try {
            return applicationService.getApplication(applicationId, accountId, true);
        } catch (ValidationException e) {
            vr.merge(e.getValidationResult());
            return null;
        }
    }

    private Publisher validatePublisher(String name, ValidationResult vr) {
        try {
            return publisherService.getActiveByName(name);
        } catch (ValidationException e) {
            vr.merge(e.getValidationResult());
            return null;
        }
    }

    private Environment validateEnvironment(Long environmentId, ValidationResult vr) {
        try {
            return environmentService.getEnvironment(environmentId, true);
        } catch (ValidationException e) {
            vr.merge(e.getValidationResult());
            return null;
        }
    }
}
