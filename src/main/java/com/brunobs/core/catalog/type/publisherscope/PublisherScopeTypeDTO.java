package com.brunobs.core.catalog.type.publisherscope;

import com.brunobs.shared.base.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;

public record PublisherScopeTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        JsonNode settings
) implements BaseTypeDTO<PublisherScopeTypeDTO, Long> {

    @Override
    public PublisherScopeTypeDTO withId(Long id) {
        return new PublisherScopeTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.settings
        );
    }
}
