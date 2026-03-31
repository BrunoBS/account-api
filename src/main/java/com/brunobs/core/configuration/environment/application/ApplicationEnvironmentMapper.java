package com.brunobs.core.configuration.environment.application;

import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.PublisherConfigDTO;
import com.brunobs.core.configuration.environment.application.dto.ApplicationEnvironmentDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationEnvironmentMapper {

    private final SchemaValidator schemaValidator;
    private final SchemaValidator schemaEngine;

    public ApplicationEnvironmentMapper(SchemaValidator schemaValidator, SchemaValidator schemaEngine) {
        this.schemaValidator = schemaValidator;
        this.schemaEngine = schemaEngine;
    }

    public ApplicationEnvironment toEntity(ApplicationEnvironmentDTO dto) {
        ApplicationEnvironment entity = new ApplicationEnvironment();
        entity.setSettings(schemaEngine.toJsonString(dto.settings()));
        entity.setEnvironmentId(dto.environment().getId());
        entity.setApplicationId(dto.application().getId());
        entity.getPublishers().clear();
        entity.getPublishers().addAll(dto.publishers());

        return entity;
    }

    public EnvironmentConfigDTO toDTO(ApplicationEnvironment entity) {
        List<PublisherConfigDTO> publishers = entity.getPublishers().stream().map(p ->
                new PublisherConfigDTO(p.getPublisher().getName(), p.getOrder(), schemaValidator.fromString(p.getParameters()))).toList();
        return new EnvironmentConfigDTO(
                entity.getEnvironmentId(),
                publishers,
                schemaEngine.fromString(entity.getSettings()));
    }

    public void updateEntity(ApplicationEnvironment entity, ApplicationEnvironmentDTO dto) {
        entity.setEnvironmentId(dto.getEnvironmentId());
        entity.setApplicationId(dto.getApplicationId());
        entity.setSettings(schemaEngine.toJsonString(dto.settings()));
        entity.getPublishers().clear();
        entity.getPublishers().addAll(dto.publishers());
    }
}
