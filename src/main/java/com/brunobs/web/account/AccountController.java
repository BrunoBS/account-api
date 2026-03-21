package com.brunobs.web.account; // conta -> account

import com.brunobs.audit.configs.Auditable;
import com.brunobs.audit.configs.IdSource;
import com.brunobs.core.account.AccountDTO;
import com.brunobs.core.account.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts") // Rota padronizada e pluralizada
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    @Auditable(action = "CREATE_ACCOUNT", source = IdSource.RESPONSE)
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDTO) {
        // withId segue o padrão de imutabilidade definido nos Records
        AccountDTO created = service.create(accountDTO.withId(null));
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> findAll() {
        List<AccountDTO> accounts = service.findAll();
        return accounts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
        AccountDTO searchDto = AccountDTO.toDTO(id, true);
        return ResponseEntity.ok(service.findByIdAndStatus(searchDto));
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
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        AccountDTO searchDto = AccountDTO.toDTO(id, true);
        service.delete(searchDto);
        return ResponseEntity.noContent().build();
    }
}
