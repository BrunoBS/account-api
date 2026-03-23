package com.brunobs.web.account; // conta -> account

import com.brunobs.audit.configs.Auditable;
import com.brunobs.audit.configs.IdSource;
import com.brunobs.core.account.AccountDTO;
import com.brunobs.core.account.AccountService;
import com.brunobs.core.onboarding.OnboardingProgressProjection;
import com.brunobs.core.onboarding.OnboardingService;
import com.brunobs.shared.RestoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts") // Rota padronizada e pluralizada
public class AccountController {

    private final AccountService service;
    private final OnboardingService onboardingService;

    public AccountController(AccountService service, OnboardingService onboardingService) {
        this.service = service;
        this.onboardingService = onboardingService;
    }

    @PostMapping
    @Auditable(action = "CREATE_ACCOUNT", source = IdSource.RESPONSE)
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDTO) {
        // withId segue o padrão de imutabilidade definido nos Records
        AccountDTO created = service.create(accountDTO.withId(null));
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> findAll(
            @RequestParam(defaultValue = "true") Boolean active) {
        List<AccountDTO> accounts = service.findAll(active);
        return accounts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/{id}/onbording")
    public ResponseEntity<List<OnboardingProgressProjection>> onboardingProgress(@PathVariable Long id) {

        return ResponseEntity.ok(onboardingService.onboardingProgress(id));
    }


    @PutMapping("/{id}")
    @Auditable(action = "UPDATE_ACCOUNT", source = IdSource.RESPONSE)
    public ResponseEntity<AccountDTO> update(
            @PathVariable Long id,
            @RequestBody AccountDTO accountDTO
    ) {
        AccountDTO updated = service.update(accountDTO.withId(id));
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    @Auditable(action = "DEACTIVATE_ACCOUNT", source = IdSource.PATH)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/restore")
    @Auditable(action = "RESTAURE_ACCOUNT", source = IdSource.PATH)
    public ResponseEntity<AccountDTO> restore(@PathVariable Long id,
                                              @RequestBody(required = false) RestoreDTO body) {
        String newName = body != null ? body.getName() : null;
        AccountDTO account = service.restore(id, newName);
        return ResponseEntity.ok(account);
    }


}
