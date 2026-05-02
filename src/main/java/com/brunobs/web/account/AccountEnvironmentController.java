package com.brunobs.web.account;

import com.brunobs.audit.configs.*;
import com.brunobs.auth.authorization.AuthorizationLevel;
import com.brunobs.auth.authorization.AuthorizationRequired;
import com.brunobs.core.environment.EnvironmentDTO;
import com.brunobs.core.environment.EnvironmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/environments")
public class AccountEnvironmentController {

    private final EnvironmentService environmentService;

    public AccountEnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @PostMapping
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    @Auditable(
            entityType = AuditEntityType.ENVIRONMENT,
            type = AuditEventType.INSERT,
            entity = @AuditField(source = IdSource.RESPONSE, field = "accountId"),
            environment = @AuditField(source = IdSource.RESPONSE, field = "id")
    )
    public ResponseEntity<EnvironmentDTO> createEnvironmentForAccount(
            @PathVariable Long accountId,
            @RequestBody EnvironmentDTO req) {
        EnvironmentDTO environmentDTO = environmentService.create(req.withId(null, accountId));
        return ResponseEntity.status(HttpStatus.CREATED).body(environmentDTO);
    }

    @GetMapping
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<List<EnvironmentDTO>> findEnvironmentsByAccount(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "true") boolean active
    ) {
        List<EnvironmentDTO> list = environmentService.findByAccount(accountId, active);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);

    }

    @GetMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<EnvironmentDTO> findEnvironmentByAccountAndId(
            @PathVariable Long accountId,
            @PathVariable Long environmentId,
            @RequestParam(defaultValue = "true") boolean active) {
        EnvironmentDTO environmentDTO = environmentService.findBy(EnvironmentDTO.of(accountId, environmentId, active));
        return ResponseEntity.ok(environmentDTO);
    }

    @PutMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    @Auditable(
            entityType = AuditEntityType.ENVIRONMENT,
            type = AuditEventType.UPDATE,
            entity = @AuditField(source = IdSource.PATH, field = "accountId"),
            environment = @AuditField(source = IdSource.PATH, field = "environmentId")
    )
    public ResponseEntity<EnvironmentDTO> updateEnvironmentForAccount(
            @PathVariable Long accountId,
            @PathVariable Long environmentId,
            @RequestBody EnvironmentDTO req) {
        EnvironmentDTO environmentDTO = environmentService.update(req.withId(environmentId, accountId));
        return ResponseEntity.ok(environmentDTO);
    }

    @DeleteMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    @Auditable(
            entityType = AuditEntityType.ENVIRONMENT,
            type = AuditEventType.DELETE,
            entity = @AuditField(source = IdSource.PATH, field = "accountId"),
            environment = @AuditField(source = IdSource.PATH, field = "environmentId")
    )
    public ResponseEntity<?> delete(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        EnvironmentDTO environmentDTO = EnvironmentDTO.of(accountId, environmentId, true);
        environmentService.delete(environmentDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{environmentId}")
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    @Auditable(
            entityType = AuditEntityType.ENVIRONMENT,
            type = AuditEventType.RESTORE,
            entity = @AuditField(source = IdSource.PATH, field = "accountId"),
            environment = @AuditField(source = IdSource.PATH, field = "environmentId")
    )

    public ResponseEntity<?> restore(
            @PathVariable Long accountId,
            @PathVariable Long environmentId) {
        EnvironmentDTO environmentDTO = environmentService.restore(EnvironmentDTO.of(accountId, environmentId, true));
        return ResponseEntity.ok(environmentDTO);

    }

}
