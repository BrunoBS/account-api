package com.brunobs.core.catalog.type.environment;

import com.brunobs.shared.BaseTypeDTO;

/**
 * Data Transfer Object for Environment Type catalog.
 * Implements BaseTypeDTO to ensure compatibility with generic mappers.
 */
public record EnvironmentTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder
) implements BaseTypeDTO<EnvironmentTypeDTO, Long> {

    @Override
    public EnvironmentTypeDTO withId(Long id) {
        return new EnvironmentTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder
        );
    }

    // Os métodos registrationIdentifier() e registrationName()
    // já são resolvidos via default methods na interface BaseTypeDTO.
}
