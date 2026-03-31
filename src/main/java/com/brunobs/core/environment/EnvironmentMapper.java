package com.brunobs.core.environment;


import com.brunobs.core.account.Account;
import com.brunobs.core.catalog.type.authorization.AuthorizationType;
import com.brunobs.core.catalog.type.environment.EnvironmentType;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentMapper {

    public Environment toEntity(EnvironmentDTO dto,
                                Account account,
                                EnvironmentType type,
                                AuthorizationType authorizationType) {
        if (dto == null) return null;

        Environment entity = new Environment();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setActive(true);
        entity.setSortOrder(dto.sortOrder());

        // Relacionamentos
        entity.setAccount(account);
        entity.setType(type);
        entity.setAuthorizationType(authorizationType);

        return entity;
    }

    public EnvironmentDTO toDTO(Environment entity) {
        if (entity == null) return null;

        return new EnvironmentDTO(
                entity.getId(),
                entity.getAccount() != null ? entity.getAccount().getId() : null,
                entity.getName(),
                entity.getAuthorizationType() != null ? entity.getAuthorizationType().getName() : null,
                entity.getType() != null ? entity.getType().getName() : null,
                entity.getDescription(),
                entity.getSortOrder()
        );
    }

    public void updateEntity(Environment entity,
                             EnvironmentDTO dto,
                             Account account,
                             EnvironmentType type,
                             AuthorizationType authorizationType) {
        if (entity == null || dto == null) return;

        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setActive(true);

        // Atualiza relacionamentos
        entity.setAccount(account);
        entity.setType(type);
        entity.setAuthorizationType(authorizationType);

        // Lógica de fallback para campos numéricos
        if (dto.sortOrder() != null) {
            entity.setSortOrder(dto.sortOrder());
        }
    }
}
