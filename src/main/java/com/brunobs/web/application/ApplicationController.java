package com.brunobs.web.application; // aplicacao -> application

import com.brunobs.audit.configs.Auditable;
import com.brunobs.audit.configs.IdSource;
import com.brunobs.core.application.ApplicationDTO;
import com.brunobs.core.application.ApplicationService;
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
    public ResponseEntity<ApplicationDTO> create(
            @PathVariable Long accountId,
            @RequestBody ApplicationDTO applicationDTO
    ) {
        ApplicationDTO created = service.create(applicationDTO.withId(null, accountId));
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> findAll(@PathVariable Long accountId) {
        List<ApplicationDTO> applications = service.findAll();
        return applications.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> findById(
            @PathVariable Long accountId,
            @PathVariable Long id) {
        ApplicationDTO searchDto = ApplicationDTO.toDTO(id, accountId, true);
        ApplicationDTO application = service.findById(searchDto);
        return ResponseEntity.ok(application);
    }

    @PutMapping("/{id}")
    @Auditable(action = "UPDATE_APPLICATION", source = IdSource.RESPONSE)
    public ResponseEntity<ApplicationDTO> update(
            @PathVariable Long accountId,
            @PathVariable Long id,
            @RequestBody ApplicationDTO applicationDTO
    ) {
        ApplicationDTO updated = service.update(applicationDTO.withId(id, accountId));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Auditable(action = "DEACTIVATE_APPLICATION", source = IdSource.RESPONSE)
    public ResponseEntity<Void> deactivate(
            @PathVariable Long accountId,
            @PathVariable Long id) {
        ApplicationDTO searchDto = ApplicationDTO.toDTO(id, accountId, true);
        service.delete(searchDto);
        return ResponseEntity.noContent().build();
    }
}
