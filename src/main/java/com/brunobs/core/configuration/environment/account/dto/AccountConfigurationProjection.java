package com.brunobs.core.configuration.environment.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;

/**
 * Projection for Account Environment configurations.
 * Naming matches the Aliases defined in the SQL query.
 */
public interface AccountConfigurationProjection {

    @JsonProperty(index = 1)
    Integer getIndexRow();

    @JsonProperty(index = 2)
    Long getAccountId();

    @JsonProperty(index = 3)
    String getAccountName();

    @JsonProperty(index = 4)
    Long getEnvironmentId();

    @JsonProperty(index = 5)
    String getEnvironmentName();

    @JsonProperty(index = 6)
    String getEnvironmentTypeName();

    @JsonProperty(index = 7)
    String getEnvironmentTypeDescription();

    @JsonProperty(index = 8)
    String getAuthorizationTypeName();

    @JsonProperty(index = 9)
    String getAuthorizationTypeDescription();

    @JsonProperty(index = 10)
    String getDescription();

    @JsonProperty(index = 11)
    String getAuthorizerGroup();

    @JsonProperty(index = 12)
    @Value("#{target.isConfigured == 1}")
    Boolean getIsConfigured();

    @JsonProperty(index = 13)
    @Value("#{@schemaValidator.fromString(target.settings)}")
    JsonNode getSettings();
}
