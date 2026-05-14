package com.brunobs.web.account; // conta -> account

import com.brunobs.audit.configs.*;
import com.brunobs.auth.authorization.AuthorizationLevel;
import com.brunobs.auth.authorization.AuthorizationRequired;
import com.brunobs.core.account.AccountDTO;
import com.brunobs.core.account.AccountService;
import com.brunobs.core.onboarding.OnboardingProgressProjection;
import com.brunobs.core.onboarding.OnboardingService;
import com.brunobs.feature.configuration.account.AccountConfigurationService;
import com.brunobs.shared.RestoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService service;
    private final AccountConfigurationService accountConfigurationService;
    private final OnboardingService onboardingService;

    public AccountController(AccountService service, AccountConfigurationService accountConfigurationService, OnboardingService onboardingService) {
        this.service = service;
        this.accountConfigurationService = accountConfigurationService;
        this.onboardingService = onboardingService;
    }

    @PostMapping
    @Auditable(
            entityType = AuditEntityType.ACCOUNT,
            type = AuditEventType.INSERT,
            entity = @AuditField(source = IdSource.RESPONSE, field = "id")
    )
    @AuthorizationRequired(level = AuthorizationLevel.OPEN)
    public ResponseEntity<AccountDTO> create(
            @RequestBody AccountDTO accountDTO) {
        AccountDTO created = service.create(accountDTO.withId(null));
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @AuthorizationRequired(level = AuthorizationLevel.OPEN)
    public ResponseEntity<List<?>> findAll(
            @RequestParam(defaultValue = "true") Boolean active,
            @RequestParam(required = false) String typeName,
            @RequestParam(defaultValue = "false") Boolean simplify,
            @RequestParam(required = false) String tagName) {
        List<?> accounts = service.findAll(active, simplify, typeName, tagName);
        return accounts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<AccountDTO> findById(@PathVariable Long accountId) {
        return ResponseEntity.ok(service.findById(accountId));
    }

    @GetMapping("/{accountId}/onboarding")
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<List<OnboardingProgressProjection>> onboardingProgress(@PathVariable Long accountId) {
        return ResponseEntity.ok(onboardingService.onboardingProgress(accountId));
    }

    @PatchMapping("/{accountId}/onboarding")
    @Auditable(
            entityType = AuditEntityType.ACCOUNT,
            type = AuditEventType.UPDATE,
            entity = @AuditField(source = IdSource.PATH, field = "accountId")
    )
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public ResponseEntity<List<OnboardingProgressProjection>> onboardingUpdate(@PathVariable Long accountId) {
        service.onboardingUpdate(accountId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{accountId}")
    @Auditable(
            entityType = AuditEntityType.ACCOUNT,
            type = AuditEventType.UPDATE,
            entity = @AuditField(source = IdSource.PATH, field = "accountId")
    )
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public ResponseEntity<AccountDTO> update(
            @PathVariable Long accountId,
            @RequestBody AccountDTO accountDTO
    ) {
        AccountDTO updated = service.update(accountDTO.withId(accountId));
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{accountId}")
    @Auditable(
            entityType = AuditEntityType.ACCOUNT,
            type = AuditEventType.DELETE,
            entity = @AuditField(source = IdSource.PATH, field = "accountId")
    )
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public ResponseEntity<Void> delete(@PathVariable Long accountId) {
        service.delete(accountId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{accountId}/restore")
    @Auditable(
            entityType = AuditEntityType.ACCOUNT,
            type = AuditEventType.RESTORE,
            entity = @AuditField(source = IdSource.PATH, field = "accountId")
    )
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public ResponseEntity<AccountDTO> restore(@PathVariable Long accountId,
                                              @RequestBody(required = false) RestoreDTO body) {
        String newName = body != null ? body.getName() : null;
        AccountDTO account = service.restore(accountId, newName);
        return ResponseEntity.ok(account);
    }
}
