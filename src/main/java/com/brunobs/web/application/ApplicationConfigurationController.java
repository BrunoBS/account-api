package com.brunobs.web.application;


import com.brunobs.core.configuration.EnvironmentConfigDTO;
import com.brunobs.core.configuration.environment.application.dto.ApplicationConfigurationProjection;
import com.brunobs.features.configuration.application.ApplicationConfigurationService;
import com.brunobs.features.configuration.application.dto.ApplicationEnvironmentPublishersResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/applications/applicationId/configurations")
public class ApplicationConfigurationController {

    private final ApplicationConfigurationService service;

    public ApplicationConfigurationController(ApplicationConfigurationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EnvironmentConfigDTO> create(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestBody EnvironmentConfigDTO dto) {

        EnvironmentConfigDTO request = dto.withEnvironmentId(dto.environmentId());
        return ResponseEntity.ok(service.create(accountId,applicationId, request));
    }

    @PutMapping("/{environmentId}")
    public ResponseEntity<EnvironmentConfigDTO> update(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long environmentId,
            @RequestBody EnvironmentConfigDTO dto) {
        EnvironmentConfigDTO request = dto.withEnvironmentId(environmentId);
        return ResponseEntity.ok(service.update(accountId, applicationId, environmentId, request));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationConfigurationProjection>> findByAccount(
            @PathVariable Long accountId,
            @PathVariable Long applicationId
    ) {
        List<ApplicationConfigurationProjection> configurations = service.findByApplication(accountId, applicationId);
        return configurations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(configurations);
    }

    @GetMapping("/{environmentId}")
    public ResponseEntity<ApplicationConfigurationProjection> findByAccountAndEnvironment(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long environmentId) {
        ApplicationConfigurationProjection configuration = service.findByAccountAndEnvironment(accountId, applicationId, environmentId);
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
    public ResponseEntity<ApplicationEnvironmentPublishersResponseDTO> findPublishersByEnvironment(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long environmentId) {
        ApplicationEnvironmentPublishersResponseDTO response = service.findPublishersByEnvironment(accountId, applicationId, environmentId);
        return response == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}
