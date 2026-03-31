package com.brunobs.web.account;

import com.brunobs.config.security.AuthorizationLevel;
import com.brunobs.config.security.AuthorizationRequired;
import com.brunobs.core.environment.EnvironmentDTO;
import com.brunobs.core.environment.EnvironmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/environments-custom")
public class AccountEnvironmentController {

    private final EnvironmentService environmentService;

    public AccountEnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @PostMapping
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public EnvironmentDTO createEnvironmentForAccount(
            @PathVariable Long accountId,
            @RequestBody EnvironmentDTO req) {
        return environmentService.create(req.withId(null, accountId));
    }

    @GetMapping
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public List<EnvironmentDTO> findEnvironmentsByAccount(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "true") boolean active
    ) {
        return environmentService.findByAccount(accountId, active);
    }

    @GetMapping("/{id}")
    @AuthorizationRequired(level = AuthorizationLevel.DEV)
    public EnvironmentDTO findEnvironmentByAccountAndId(
            @PathVariable Long accountId,
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") boolean active) {
        EnvironmentDTO environmentDTO = EnvironmentDTO.of(accountId, id, active);
        return environmentService.findBy(environmentDTO);
    }

    @PutMapping("/{id}")
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public EnvironmentDTO updateEnvironmentForAccount(
            @PathVariable Long accountId,
            @PathVariable Long id,
            @RequestBody EnvironmentDTO req) {
        return environmentService.update(req.withId(id, accountId));
    }

    @DeleteMapping("/{id}")
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public void deleteEnvironmentFromAccount(
            @PathVariable Long accountId,
            @PathVariable Long id) {
        EnvironmentDTO environmentDTO = EnvironmentDTO.of(accountId, id, true);
        environmentService.delete(environmentDTO);
    }
}
