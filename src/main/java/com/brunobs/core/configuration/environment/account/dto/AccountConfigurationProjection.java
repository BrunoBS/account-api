package com.brunobs.core.configuration.environment.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * Projection for Account Environment configurations.
 * Naming matches the Aliases defined in the SQL query.
 */
public interface AccountConfigurationProjection {

    @JsonProperty(index = 1)
    Integer getIndexRow(); // indice -> index_row

    @JsonProperty(index = 2)
    Long getAccountId(); // idConta -> accountId

    @JsonProperty(index = 3)
    Long getEnvironmentId(); // idAmbiente -> environmentId

    @JsonProperty(index = 4)
    String getEnvironmentName(); // nomeAmbiente -> environmentName

    @JsonProperty(index = 5)
    String getEnvironmentTypeName(); // nomeTipoAmbiente -> environmentTypeName

    @JsonProperty(index = 6)
    String getEnvironmentTypeDescription(); // descricaoTipoAmbiente -> environmentTypeDescription

    @JsonProperty(index = 7)
    String getAuthorizationTypeName(); // nomeTipoAutorizacao -> authorizationTypeName

    @JsonProperty(index = 8)
    String getAuthorizationTypeDescription(); // descricaoTipoAutorizacao -> authorizationTypeDescription

    @JsonProperty(index = 9)
    String getDescription();

    @JsonProperty(index = 10)
    String getAuthorizerGroup(); // grupoAutorizador -> authorizerGroup

    @JsonProperty(index = 11)
    @Value("#{target.isConfigured == 1}")
    Boolean getActive(); // situacao -> active

    @JsonProperty(index = 12)
    @Value("#{target.isConfigured == 1}")
    Boolean getIsConfigured(); // isConfigurado -> isConfigured
}
