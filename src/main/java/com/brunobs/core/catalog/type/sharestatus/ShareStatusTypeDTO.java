package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.shared.BaseTypeDTO;

/**
 * Data Transfer Object for Share Status catalog.
 * Represents the current state of a sharing process (e.g., APPROVED, REJECTED).
 */
public record ShareStatusTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder
) implements BaseTypeDTO<ShareStatusTypeDTO, Long> {

    @Override
    public ShareStatusTypeDTO withId(Long id) {
        return new ShareStatusTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder
        );
    }
}
