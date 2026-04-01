package com.brunobs.core.environment;


import com.brunobs.core.account.Account;
import com.brunobs.core.catalog.type.authorization.AuthorizationType;
import com.brunobs.core.catalog.type.environment.EnvironmentType;
import com.brunobs.shared.SchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentMapper {

    private final SchemaValidator schemaValidator;

    public EnvironmentMapper(SchemaValidator schemaValidator) {
        this.schemaValidator = schemaValidator;
    }

    public Environment toEntity(EnvironmentDTO dto,
                                Account account,
                                EnvironmentType type,
                                AuthorizationType authorizationType) {
        if (dto == null) return null;
        String parametersString = schemaValidator.toJsonString(dto.settings());
        Environment entity = new Environment();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setActive(true);
        entity.setSortOrder(dto.sortOrder());
        entity.setSettings(parametersString);
        entity.setAccount(account);
        entity.setType(type);
        entity.setAuthorizerGroup(dto.authorizerGroup() == null ? "" : dto.authorizerGroup());
        entity.setAuthorizationType(authorizationType);

        return entity;
    }

    public EnvironmentDTO toDTO(Environment entity) {
        if (entity == null) return null;
        JsonNode parametersJson = schemaValidator.fromString(entity.getSettings());
        return new EnvironmentDTO(
                entity.getId(),
                entity.getAccount() != null ? entity.getAccount().getId() : null,
                entity.getName(),
                entity.getAuthorizationType() != null ? entity.getAuthorizationType().getName() : null,
                entity.getType() != null ? entity.getType().getName() : null,
                entity.getDescription(),
                entity.getSortOrder(),
                entity.getAuthorizerGroup(),
                parametersJson
        );
    }

    public void updateEntity(Environment entity,
                             EnvironmentDTO dto,
                             Account account,
                             EnvironmentType type,
                             AuthorizationType authorizationType) {
        if (entity == null || dto == null) return;
        String parametersString = schemaValidator.toJsonString(dto.settings());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setActive(true);
        entity.setSettings(parametersString);
        entity.setAccount(account);
        entity.setType(type);
        entity.setAuthorizationType(authorizationType);
        entity.setAuthorizerGroup(dto.authorizerGroup() == null ? "" : dto.authorizerGroup());
        if (dto.sortOrder() != null) {
            entity.setSortOrder(dto.sortOrder());
        }
    }
}
