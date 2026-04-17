package com.brunobs.core.environment;

import com.brunobs.core.catalog.type.environment.EnvironmentTypeEnum;
import com.brunobs.shared.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EnvironmentDTO(

        @JsonProperty("id")
        Long id,

        @JsonProperty("account_id")
        Long accountId,

        @JsonProperty("name")
        String name,

        @JsonProperty("authorizationType")
        String authorizationType,

        @JsonProperty("environmentType")
        String environmentType,

        @JsonProperty("description")
        String description,

        @JsonProperty("sortOrder")
        Integer sortOrder,

        @JsonProperty("authorizerGroup")
        String authorizerGroup,
        JsonNode settings

) implements BaseDTO<String, Long> {

    @Override
    public Long registrationIdentifier() {
        return id;
    }

    @Override
    public String registrationName() {
        return name;
    }


    public EnvironmentDTO withId(Long id, Long accountId) {
        return new EnvironmentDTO(
                id,
                accountId,
                this.name,
                this.authorizationType,
                (accountId == null) ? EnvironmentTypeEnum.DEFAULT.name() : EnvironmentTypeEnum.CUSTOM.name(),
                this.description,
                this.sortOrder,
                this.authorizerGroup,
                this.settings
        );
    }


    public static EnvironmentDTO of(Long accountId, Long id, boolean active) {
        return new EnvironmentDTO(
                id,
                accountId,
                null,
                null,
                (accountId == null) ? EnvironmentTypeEnum.DEFAULT.name() : EnvironmentTypeEnum.CUSTOM.name(),
                null,
                null,
                null,
                null
        );
    }
}
