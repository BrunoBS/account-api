package com.brunobs.web.application; // aplicacao -> application

import com.brunobs.audit.configs.Auditable;
import com.brunobs.audit.configs.IdSource;
import com.brunobs.config.context.UserContext;
import com.brunobs.config.context.UserSession;
import com.brunobs.config.security.AuthorizationLevel;
import com.brunobs.config.security.AuthorizationRequired;
import com.brunobs.core.account.AccountDTO;
import com.brunobs.core.application.ApplicationDTO;
import com.brunobs.core.application.ApplicationService;
import com.brunobs.shared.RestoreDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/applications") // Rota padronizada e versionada
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @Auditable(action = "CREATE_APPLICATION", source = IdSource.RESPONSE)
    @AuthorizationRequired(level = AuthorizationLevel.ADM)
    public ResponseEntity<ApplicationDTO> create(
            @PathVariable Long accountId,
            @RequestBody ApplicationDTO applicationDTO
    ) {
        UserSession session = UserContext.get();
        ApplicationDTO created = service.create(applicationDTO.withId(null, accountId));
        return ResponseEntity.ok(created);
    }


    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> findByAccountIdAndActive(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "true") Boolean active
    ) {
        ApplicationDTO searchDto = ApplicationDTO.toDTO(null, accountId);
        List<ApplicationDTO> applications = service.findByAccountIdAndActive(searchDto, active);

        return applications.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(applications);
    }


    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationDTO> findById(
            @PathVariable Long accountId,
            @PathVariable Long applicationId
    ) {
        ApplicationDTO searchDto = ApplicationDTO.toDTO(applicationId, accountId);
        ApplicationDTO application = service.findById(searchDto);
        return ResponseEntity.ok(application);
    }

    @PutMapping("/{applicationId}")
    @Auditable(action = "UPDATE_APPLICATION", source = IdSource.RESPONSE)
    public ResponseEntity<ApplicationDTO> update(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestBody ApplicationDTO applicationDTO
    ) {
        ApplicationDTO updated = service.update(applicationDTO.withId(applicationId, accountId));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{applicationId}")
    @Auditable(action = "DEACTIVATE_APPLICATION", source = IdSource.RESPONSE)
    public ResponseEntity<Void> deactivate(
            @PathVariable Long accountId,
            @PathVariable Long applicationId) {
        ApplicationDTO searchDto = ApplicationDTO.toDTO(applicationId, accountId);
        service.delete(searchDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{applicationId}/restore")
    @Auditable(action = "RESTAURE_APPLICATION", source = IdSource.PATH)
    public ResponseEntity<ApplicationDTO> restore(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestBody(required = false) RestoreDTO body) {
        String newName = body != null ? body.getName() : null;
        ApplicationDTO account = service.restore(accountId, applicationId, newName);
        return ResponseEntity.ok(account);
    }
}
