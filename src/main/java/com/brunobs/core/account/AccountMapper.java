package com.brunobs.core.account;

import com.brunobs.core.catalog.type.account.AccountType;
import com.brunobs.shared.SchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    private final SchemaValidator schemaValidator;

    public AccountMapper(SchemaValidator schemaValidator) {
        this.schemaValidator = schemaValidator;
    }

    public Account toEntity(AccountDTO dto, AccountType type) {
        Account entity = new Account();
        entity.setId(dto.id());
        entity.setIdentifier(dto.identifier() == null ? UUID.randomUUID() : dto.identifier());

        updateEntityFields(entity, dto, type);
        return entity;
    }

    public AccountDTO toDTO(Account entity) {
        JsonNode parametersJson = schemaValidator.fromString(entity.getParameters());
        List<ApproverDTO> approvers = entity.getApprovers().stream()
                .map(a -> new ApproverDTO(a.getEmployeeId(), a.getEmail()))
                .collect(Collectors.toList());

        List<String> tags = entity.getTags().stream()
                .map(AccountTag::getName)
                .collect(Collectors.toList());

        return new AccountDTO(
                entity.getId(),
                entity.getIdentifier(),
                entity.getType() != null ? entity.getType().getName() : null,
                entity.getName(),
                entity.getDescription(),
                entity.getRequester(),
                entity.getInitials(),
                entity.getAuthorizerGroup(),
                parametersJson,
                entity.getEmailGroup(),
                entity.isActive(),
                approvers,
                tags
        );
    }

    public void updateEntity(Account entity, AccountDTO dto, AccountType type) {
        updateEntityFields(entity, dto, type);
    }

    private void updateEntityFields(Account entity, AccountDTO dto, AccountType type) {
        String parametersString = schemaValidator.toJsonString(dto.parameters());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setRequester(dto.requester());
        entity.setInitials(dto.initials());
        entity.setAuthorizerGroup(dto.authorizerGroup());
        entity.setType(type);
        entity.setParameters(parametersString);
        entity.setEmailGroup(dto.emailGroup());
        entity.setActive(dto.active());

        // Sincronização de Approvers
        entity.getApprovers().clear();
        getApprovers(entity, dto).forEach(entity::addApprover);

        // Sincronização de Tags
        entity.getTags().clear();
        getTags(entity, dto).forEach(entity::addTag);
    }

    @Nonnull
    private static List<Approver> getApprovers(Account entity, AccountDTO dto) {
        if (dto.approvers() == null) return new ArrayList<>();
        return dto.approvers().stream()
                .map(aDto -> new Approver(aDto.employeeId(), aDto.email(), entity))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<AccountTag> getTags(Account entity, AccountDTO dto) {
        if (dto.tags() == null) return new ArrayList<>();
        return dto.tags().stream()
                .filter(tag -> tag != null && !tag.isBlank())
                .map(String::trim)
                .map(String::toLowerCase)
                .distinct()
                .map(tag -> new AccountTag(tag, entity))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
