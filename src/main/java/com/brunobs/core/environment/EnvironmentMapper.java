package com.brunobs.core.environment;


import com.brunobs.core.account.Account;
import com.brunobs.core.catalog.type.authorization.AuthorizationType;
import com.brunobs.core.catalog.type.environment.EnvironmentType;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentMapper {

    /**
     * Converte DTO para Entidade.
     * Guideline: Entidades relacionadas (Account, Type) são passadas como dependências resolvidas.
     */
    public Environment toEntity(EnvironmentDTO dto,
                                Account account,
                                EnvironmentType type,
                                AuthorizationType authorizationType) {
        if (dto == null) return null;

        Environment entity = new Environment();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setActive(dto.active());
        entity.setSortOrder(dto.sortOrder());

        // Relacionamentos
        entity.setAccount(account);
        entity.setType(type);
        entity.setAuthorizationType(authorizationType);

        return entity;
    }

    /**
     * Converte Entidade para DTO.
     * Guideline: Proteção contra NullPointer e simplificação de tipos complexos para primitivos/Strings.
     */
    public EnvironmentDTO toDTO(Environment entity) {
        if (entity == null) return null;

        return new EnvironmentDTO(
                entity.getId(),
                entity.getAccount() != null ? entity.getAccount().getId() : null,
                entity.getName(),
                entity.getAuthorizationType() != null ? entity.getAuthorizationType().getName() : null,
                entity.getType() != null ? entity.getType().getName() : null,
                entity.getDescription(),
                entity.getSortOrder(),
                entity.isActive()
        );
    }

    /**
     * Atualiza uma entidade existente (Partial Update).
     * Guideline: Verifica nulidade para campos opcionais e preserva o estado da entidade.
     */
    public void updateEntity(Environment entity,
                             EnvironmentDTO dto,
                             Account account,
                             EnvironmentType type,
                             AuthorizationType authorizationType) {
        if (entity == null || dto == null) return;

        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setActive(dto.active());

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
