package com.brunobs.core.configuration.environment.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;

/**
 * Projection for Account Environment configurations.
 * Naming matches the Aliases defined in the SQL query.
 */
public interface ApplicationConfigurationProjection {


    @JsonProperty(index = 1)
    Integer getIndexRow();

    @JsonProperty(index = 2)
    Long getAccountId();

    @JsonProperty(index = 3)
    String getAccountName();

    @JsonProperty(index = 4)
    Long getApplicationId();

    @JsonProperty(index = 5)
    String getAplicationName();

    @JsonProperty(index = 6)
    Long getEnvironmentId();

    @JsonProperty(index = 7)
    String getEnvironmentName();

    @JsonProperty(index = 8)
    String getEnvironmentTypeName();

    @JsonProperty(index = 9)
    String getEnvironmentTypeDescription();

    @JsonProperty(index = 10)
    String getAuthorizationTypeName();

    @JsonProperty(index = 11)
    String getAuthorizationTypeDescription();

    @JsonProperty(index = 12)
    String getDescription();

    @JsonProperty(index = 13)
    String getAuthorizerGroup();

    @JsonProperty(index = 14)
    @Value("#{target.isConfigured == 1}")
    Boolean getIsConfigured();

    @JsonProperty(index = 15)
    @Value("#{@schemaValidator.fromString(target.settings)}")
    JsonNode getSettings();
}
