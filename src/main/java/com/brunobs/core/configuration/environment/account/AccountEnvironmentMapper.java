package com.brunobs.core.configuration.environment.account;

import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.environment.account.dto.AccountEnvironmentDTO;
import com.brunobs.core.configuration.PublisherConfigDTO;
import com.brunobs.shared.SchemaValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountEnvironmentMapper {

    private final SchemaValidator schemaValidator;
    private final SchemaValidator schemaEngine;

    public AccountEnvironmentMapper(SchemaValidator schemaValidator, SchemaValidator schemaEngine) {
        this.schemaValidator = schemaValidator;
        this.schemaEngine = schemaEngine;
    }

    public AccountEnvironment toEntity(AccountEnvironmentDTO dto) {
        AccountEnvironment entity = new AccountEnvironment();
        entity.setSettings(schemaEngine.toJsonString(dto.settings()));
        entity.setEnvironmentId(dto.environment().getId());
        entity.setAccountId(dto.account().getId());


        entity.getPublishers().clear();
        entity.getPublishers().addAll(dto.publishers());

        return entity;
    }

    public EnvironmentConfigDTO toDTO(AccountEnvironment entity) {
        List<PublisherConfigDTO> publishers = entity.getPublishers().stream()
                .map(p -> new PublisherConfigDTO(
                        p.getPublisher().getName(),
                        p.getOrder(),
                        schemaValidator.fromString(p.getParameters())
                )).toList();

        return new EnvironmentConfigDTO(
                entity.getEnvironmentId(),
                publishers,
                schemaEngine.fromString(entity.getSettings()));
    }

    public void updateEntity(AccountEnvironment entity, AccountEnvironmentDTO dto) {
        entity.setEnvironmentId(dto.getEnvironmentId());
        entity.setAccountId(dto.getAccountId());
        entity.setSettings(schemaEngine.toJsonString(dto.settings()));
        entity.getPublishers().clear();
        entity.getPublishers().addAll(dto.publishers());
    }
}
