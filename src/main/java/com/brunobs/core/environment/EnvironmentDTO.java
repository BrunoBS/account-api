package com.brunobs.core.environment;

import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.shared.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for Environment information.
 * Uses snake_case for JSON strategy to follow REST industry standards.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EnvironmentDTO(

        @JsonProperty("id")
        Long id,

        @JsonProperty("account_id")
        Long accountId,

        @JsonProperty("name")
        String name,

        @JsonProperty("authorization_type")
        String authorizationType,

        @JsonProperty("environment_type")
        String environmentType,

        @JsonProperty("description")
        String description,

        @JsonProperty("sort_order")
        Integer sortOrder

) implements BaseDTO<String, Long> {

    @Override
    public Long registrationIdentifier() {
        return id;
    }

    @Override
    public String registrationName() {
        return name;
    }

    /**
     * Wither-style method to create a new instance with a specific ID and Account.
     */
    public EnvironmentDTO withId(Long id, Long accountId) {
        return new EnvironmentDTO(
                id,
                accountId,
                this.name,
                this.authorizationType,
                (accountId == null) ? EnvironmentTypeEnum.DEFAULT.name() : EnvironmentTypeEnum.CUSTOM.name(),
                this.description,
                this.sortOrder
        );
    }

    /**
     * Factory method for minimal EnvironmentDTO creation.
     */
    public static EnvironmentDTO of(Long accountId, Long id, boolean active) {
        return new EnvironmentDTO(
                id,
                accountId,
                null,
                null,
                (accountId == null) ? EnvironmentTypeEnum.DEFAULT.name() : EnvironmentTypeEnum.CUSTOM.name(),
                null,
                null
        );
    }
}
