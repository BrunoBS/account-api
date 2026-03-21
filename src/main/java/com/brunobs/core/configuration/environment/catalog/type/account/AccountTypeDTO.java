package com.brunobs.core.catalog.type.account;

import com.brunobs.shared.BaseTypeDTO;


public record AccountTypeDTO(
        Long id,
        String name,
        String label,
        String description,
        Integer sortOrder,
        boolean active
) implements BaseTypeDTO<AccountTypeDTO, Long> {

    @Override
    public AccountTypeDTO withId(Long id) {
        return new AccountTypeDTO(
                id,
                this.name,
                this.label,
                this.description,
                this.sortOrder,
                this.active
        );
    }
}
