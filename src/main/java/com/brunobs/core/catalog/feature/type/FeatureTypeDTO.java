package com.brunobs.core.catalog.feature.type;

import com.brunobs.shared.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;


public record FeatureTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        boolean active,
        JsonNode settings,
        String scope,
        Boolean available
) implements BaseTypeDTO<FeatureTypeDTO, Long> {

    @Override
    public FeatureTypeDTO withId(Long id) {
        return new FeatureTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.active,
                this.settings,
                this.scope,
                this.available
        );
    }
}
