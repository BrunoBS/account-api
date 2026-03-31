package com.brunobs.core.account;

import com.brunobs.core.catalog.type.account.AccountType;
import com.brunobs.shared.SchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        updateEntityFields(entity, dto, type);
        return entity;
    }

    public AccountDTO toDTO(Account entity) {
        JsonNode parametersJson = schemaValidator.fromString(entity.getSettings());
        Set<ApproverDTO> approvers = entity.getApprovers().stream()
                .map(a -> new ApproverDTO(a.getFuncional(), a.getEmail()))
                .collect(Collectors.toSet());

        Set<String> tags = entity.getTags().stream()
                .map(AccountTag::getName)
                .collect(Collectors.toSet());

        return new AccountDTO(
                entity.getId(),
                entity.getIdentifier().toString(),
                entity.getAccountType() != null ? entity.getAccountType().getName() : null,
                entity.getName(),
                entity.getDescription(),
                entity.getRequester(),
                entity.getAcronym(),
                entity.getAuthorizerGroup(),
                parametersJson,
                entity.getEmailGroup(),
                approvers,
                tags
        );
    }

    public void updateEntity(Account entity, AccountDTO dto, AccountType type) {
        updateEntityFields(entity, dto, type);
    }

    private void updateEntityFields(Account entity, AccountDTO dto, AccountType type) {
        String parametersString = schemaValidator.toJsonString(dto.settings());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setRequester(dto.requester());
        entity.setAcronym(dto.acronym());
        entity.setAuthorizerGroup(dto.authorizerGroup());
        entity.setAccountType(type);
        entity.setSettings(parametersString);
        entity.setEmailGroup(dto.emailGroup());

        // Sincronização de Approvers
        entity.getApprovers().clear();
        getApprovers(entity, dto).forEach(entity::addApprover);

        // Sincronização de Tags
        entity.getTags().clear();
        getTags(entity, dto).forEach(entity::addTag);
    }

    @Nonnull
    private static List<AccountAprover> getApprovers(Account entity, AccountDTO dto) {
        if (dto.approvers() == null) return new ArrayList<>();
        return dto.approvers().stream()
                .map(aDto -> new AccountAprover(aDto.funcional(), aDto.email(), entity))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<AccountTag> getTags(Account entity, AccountDTO dto) {


        dto.tags().add(entity.getIdentifier());
        dto.tags().add(dto.name());
        dto.tags().add(dto.authorizerGroup());
        dto.tags().add(dto.acronym());
        return dto.tags().stream()
                .filter(tag -> tag != null && !tag.isBlank())
                .map(String::trim)
                .map(String::toLowerCase)
                .map(tag -> tag.replace(" ", "-"))
                .distinct()
                .map(tag -> new AccountTag(tag, entity))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
