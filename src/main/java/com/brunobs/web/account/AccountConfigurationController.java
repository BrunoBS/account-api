package com.brunobs.web.account;


import com.brunobs.auth.authorization.AuthorizationLevel;
import com.brunobs.auth.authorization.AuthorizationRequired;
import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.environment.account.dto.AccountConfigurationProjection;
import com.brunobs.feature.configuration.account.AccountConfigurationService;
import com.brunobs.feature.configuration.account.dto.AccountEnvironmentPublishersResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/environments-config") // Rota padronizada e versionada
public class AccountConfigurationController {

    private final AccountConfigurationService service;

    public AccountConfigurationController(AccountConfigurationService service) {
        this.service = service;
    }

    @GetMapping()
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<List<AccountConfigurationProjection>> findByAccount(@PathVariable Long accountId) {
        List<AccountConfigurationProjection> configurations = service.findByAccount(accountId);
        return configurations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(configurations);
    }


    @PutMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public ResponseEntity<EnvironmentConfigDTO> configuration(
            @PathVariable Long accountId,
            @PathVariable Long environmentId,
            @RequestBody EnvironmentConfigDTO dto) {
        EnvironmentConfigDTO request = dto.withEnvironmentId(environmentId);
        return ResponseEntity.ok(service.configuration(accountId, request));
    }



    @GetMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<AccountConfigurationProjection> findByAccountAndEnvironment(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        AccountConfigurationProjection configuration = service.findByAccountAndEnvironment(accountId, environmentId);
        return configuration == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(configuration);
    }



    @DeleteMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public ResponseEntity<Void> delete(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        service.delete(accountId, environmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{environmentId}/publishers")
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<AccountEnvironmentPublishersResponseDTO> findPublishersByEnvironment(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        AccountEnvironmentPublishersResponseDTO response = service.findPublishersByEnvironment(accountId, environmentId);
        return response == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }


}
