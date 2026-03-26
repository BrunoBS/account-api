package com.brunobs.core.catalog.type.infrastructure;

import com.brunobs.shared.base.BaseTypeDTO;

/**
 * Data Transfer Object for Infrastructure Type catalog.
 * Includes 'label' for display and 'name' for technical identification.
 */
public record InfrastructureTypeDTO(
        Long id,
        String name,
        String label,       // Nome amigável para exibição (ex: "Máquina Virtual")
        String description,
        Integer sortOrder
) implements BaseTypeDTO<InfrastructureTypeDTO, Long> {

    @Override
    public InfrastructureTypeDTO withId(Long id) {
        return new InfrastructureTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder
        );
    }
}
