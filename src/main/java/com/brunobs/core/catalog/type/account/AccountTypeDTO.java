package com.brunobs.core.catalog.type.account;

import com.brunobs.shared.base.BaseTypeDTO;
import com.fasterxml.jackson.databind.JsonNode;


public record AccountTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        JsonNode settings

) implements BaseTypeDTO<AccountTypeDTO, Long> {

    @Override
    public AccountTypeDTO withId(Long id) {
        return new AccountTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.settings
        );
    }
}
