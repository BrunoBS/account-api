package com.brunobs.web.account;


import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.features.configuration.account.AccountConfigurationService;
import com.brunobs.features.configuration.account.dto.AccountEnvironmentPublishersResponseDTO;
import com.brunobs.core.configuration.environment.account.dto.AccountConfigurationProjection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/configurations") // Rota padronizada e versionada
public class AccountConfigurationController {

    private final AccountConfigurationService service;

    public AccountConfigurationController(AccountConfigurationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EnvironmentConfigDTO> create(
            @PathVariable Long accountId,
            @RequestBody EnvironmentConfigDTO dto) {

        EnvironmentConfigDTO request = dto.withEnvironmentId(dto.environmentId());
        return ResponseEntity.ok(service.create(accountId, request));
    }

    @PutMapping("/{environmentId}")
    public ResponseEntity<EnvironmentConfigDTO> update(
            @PathVariable Long accountId,
            @PathVariable Long environmentId,
            @RequestBody EnvironmentConfigDTO dto) {
        EnvironmentConfigDTO request = dto.withEnvironmentId(environmentId);
        return ResponseEntity.ok(service.update(accountId, environmentId, request));
    }

    @GetMapping
    public ResponseEntity<List<AccountConfigurationProjection>> findByAccount(
            @PathVariable Long accountId) {
        List<AccountConfigurationProjection> configurations = service.findByAccount(accountId);
        return configurations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(configurations);
    }

    @GetMapping("/{environmentId}")
    public ResponseEntity<AccountConfigurationProjection> findByAccountAndEnvironment(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        AccountConfigurationProjection configuration = service.findByAccountAndEnvironment(accountId, environmentId);
        return configuration == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(configuration);
    }

    @DeleteMapping("/{environmentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        service.delete(accountId, environmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{environmentId}/publishers")
    public ResponseEntity<AccountEnvironmentPublishersResponseDTO> findPublishersByEnvironment(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        AccountEnvironmentPublishersResponseDTO response = service.findPublishersByEnvironment(accountId, environmentId);
        return response == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}
