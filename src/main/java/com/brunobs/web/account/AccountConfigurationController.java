package com.brunobs.web.account;


import com.brunobs.config.security.AuthorizationLevel;
import com.brunobs.config.security.AuthorizationRequired;
import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.environment.account.dto.AccountConfigurationProjection;
import com.brunobs.feature.configuration.account.AccountConfigurationService;
import com.brunobs.feature.configuration.account.dto.AccountEnvironmentPublishersResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/environments") // Rota padronizada e versionada
public class AccountConfigurationController {

    private final AccountConfigurationService service;

    public AccountConfigurationController(AccountConfigurationService service) {
        this.service = service;
    }

    @PostMapping
    @AuthorizationRequired(level = AuthorizationLevel.TST)
    public ResponseEntity<EnvironmentConfigDTO> create(
            @PathVariable Long accountId,
            @RequestBody EnvironmentConfigDTO dto) {

        EnvironmentConfigDTO request = dto.withEnvironmentId(dto.environmentId());
        return ResponseEntity.ok(service.create(accountId, request));
    }

    @PutMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public ResponseEntity<EnvironmentConfigDTO> update(
            @PathVariable Long accountId,
            @PathVariable Long environmentId,
            @RequestBody EnvironmentConfigDTO dto) {
        EnvironmentConfigDTO request = dto.withEnvironmentId(environmentId);
        return ResponseEntity.ok(service.update(accountId, environmentId, request));
    }

    @GetMapping
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<List<AccountConfigurationProjection>> findByAccount(
            @PathVariable Long accountId) {
        List<AccountConfigurationProjection> configurations = service.findByAccount(accountId);
        return configurations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(configurations);
    }

    @GetMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<AccountConfigurationProjection> findByAccountAndEnvironment(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        AccountConfigurationProjection configuration = service.findByAccountAndEnvironment(accountId, environmentId);
        return configuration == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(configuration);
    }

    @GetMapping("/{environmentId}/publishers")
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<AccountEnvironmentPublishersResponseDTO> findPublishersByEnvironment(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        AccountEnvironmentPublishersResponseDTO response = service.findPublishersByEnvironment(accountId, environmentId);
        return response == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @DeleteMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public ResponseEntity<Void> delete(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        service.delete(accountId, environmentId);
        return ResponseEntity.noContent().build();
    }


}
