package com.brunobs.core.configuration.environment.application;

import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.environment.account.dto.PublisherConfigDTO;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentDTO;
import com.brunobs.shared.validation.SchemaValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationEnvironmentMapper {

    private final SchemaValidator schemaValidator;

    public ApplicationEnvironmentMapper(SchemaValidator schemaValidator) {
        this.schemaValidator = schemaValidator;
    }

    public ApplicationEnvironment toEntity(ApplicationEnvironmentDTO dto) {
        ApplicationEnvironment entity = new ApplicationEnvironment();

        entity.setEnvironmentId(dto.environment().getId());
        entity.setApplicationId(dto.application().getId());
        entity.setAuthorizerGroup(dto.authorizerGroup());
        entity.getPublishers().clear();
        entity.getPublishers().addAll(dto.publishers());

        return entity;
    }

    public EnvironmentConfigDTO toDTO(ApplicationEnvironment entity) {
        List<PublisherConfigDTO> publishers = entity.getPublishers().stream().map(p -> new PublisherConfigDTO(p.getPublisher().getName(), p.getOrder(), schemaValidator.fromString(p.getParameters()))).toList();

        return new EnvironmentConfigDTO(entity.getEnvironmentId(), entity.getAuthorizerGroup(), publishers);
    }

    public void updateEntity(ApplicationEnvironment entity, ApplicationEnvironmentDTO dto) {
        entity.setEnvironmentId(dto.getEnvironmentId());
        entity.setApplicationId(dto.getApplicationId());
        entity.setAuthorizerGroup(dto.authorizerGroup());

        entity.getPublishers().clear();
        entity.getPublishers().addAll(dto.publishers());
    }
}
