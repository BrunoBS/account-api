package com.brunobs.core.application;

import com.brunobs.core.account.Account;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeType;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureType;
import com.brunobs.core.catalog.type.language.LanguageType;
import com.brunobs.shared.SchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApplicationMapper {

    private final SchemaValidator schemaValidator;

    public ApplicationMapper(SchemaValidator schemaValidator) {
        this.schemaValidator = schemaValidator;
    }

    public Application toEntity(ApplicationDTO dto,
                                Account account,
                                LanguageType languageType,
                                ApplicationScopeType scopeType,
                                InfrastructureType infrastructureType) {

        Application entity = new Application();
        entity.setId(dto.id());
        entity.setIdentifier(dto.registrationIdentifier() == null ? UUID.randomUUID() : dto.identifier());

        updateFields(entity, dto, account, languageType, scopeType, infrastructureType);
        return entity;
    }

    public ApplicationDTO toDTO(Application entity) {
        List<String> tags = entity.getTags()
                .stream()
                .map(ApplicationTag::getName)
                .collect(Collectors.toList());

        JsonNode parametersJson = schemaValidator.fromString(entity.getParameters());

        return new ApplicationDTO(
                entity.getId(),
                entity.getIdentifier(),
                entity.getName(),
                entity.getAlias(),
                entity.getAccount() != null ? entity.getAccount().getId() : null,
                entity.getLanguageType() != null ? entity.getLanguageType().getName() : null,
                entity.getApplicationScopeType() != null ? entity.getApplicationScopeType().getName() : null,
                entity.getInfrastructureType() != null ? entity.getInfrastructureType().getName() : null,
                entity.getAuthorizerGroup(),
                parametersJson,
                entity.isDefault(),
                tags
        );
    }

    public void updateEntity(Application entity,
                             ApplicationDTO dto,
                             Account account,
                             LanguageType languageType,
                             ApplicationScopeType scopeType,
                             InfrastructureType infrastructureType) {

        updateFields(entity, dto, account, languageType, scopeType, infrastructureType);
    }

    private void updateFields(Application entity,
                              ApplicationDTO dto,
                              Account account,
                              LanguageType languageType,
                              ApplicationScopeType scopeType,
                              InfrastructureType infrastructureType) {

        String parametersString = schemaValidator.toJsonString(dto.parameters());

        entity.setName(dto.name());
        entity.setAlias(dto.alias());
        entity.setParameters(parametersString);
        entity.setAuthorizerGroup(dto.authorizerGroup());
        entity.setDefault(dto.isDefault());

        // Associations
        entity.setAccount(account);
        entity.setLanguageType(languageType);
        entity.setApplicationScopeType(scopeType);
        entity.setInfrastructureType(infrastructureType);

        // Tags Synchronization
        entity.getTags().clear();
        getTags(entity, dto).forEach(entity::addTag);
    }

    private static List<ApplicationTag> getTags(Application entity, ApplicationDTO dto) {
        if (dto.tags() == null) {
            return new ArrayList<>();
        }
        return dto.tags().stream()
                .filter(tag -> tag != null && !tag.isBlank())
                .map(String::trim)
                .distinct()
                .map(tag -> new ApplicationTag(tag, entity))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
