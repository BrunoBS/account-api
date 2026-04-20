package com.brunobs.web.environment; // ambiente -> environment

import com.brunobs.auth.authorization.AuthorizationLevel;
import com.brunobs.auth.authorization.AuthorizationRequired;
import com.brunobs.core.environment.EnvironmentDTO;
import com.brunobs.core.environment.EnvironmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/environment-defaults") // Rota padronizada e versionada
public class DefaultEnvironmentController {

    private final EnvironmentService environmentService;

    public DefaultEnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @PostMapping
    @AuthorizationRequired(level = AuthorizationLevel.OWNER)
    public ResponseEntity<List<EnvironmentDTO>> create(@RequestBody List<EnvironmentDTO> dtos) {
        List<EnvironmentDTO> list = dtos.stream()
                .map(dto -> environmentService.create(dto.withId(null, null)))
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<List<EnvironmentDTO>> findAll(
            @RequestParam(defaultValue = "true") boolean active) {
        List<EnvironmentDTO> list = environmentService.findAllDefault(active);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentDTO> findById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") boolean active) {
        EnvironmentDTO searchDto = EnvironmentDTO.of(null, id, active);
        EnvironmentDTO environmentDTO = environmentService.findBy(searchDto);
        return ResponseEntity.ok(environmentDTO);
    }

    @PutMapping("/{id}")
    @AuthorizationRequired(level = AuthorizationLevel.OWNER)
    public ResponseEntity<EnvironmentDTO> update(
            @PathVariable Long id,
            @RequestBody EnvironmentDTO request) {
        EnvironmentDTO environmentDTO = environmentService.update(request.withId(id, null));
        return ResponseEntity.ok(environmentDTO);

    }

    @DeleteMapping("/{id}")
    @AuthorizationRequired(level = AuthorizationLevel.OWNER)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        EnvironmentDTO searchDto = EnvironmentDTO.of(null, id, true);
        environmentService.delete(searchDto);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/restore")
    @AuthorizationRequired(level = AuthorizationLevel.OWNER)
    public ResponseEntity<EnvironmentDTO> restore(@PathVariable Long id) {
        EnvironmentDTO searchDto = EnvironmentDTO.of(null, id, true);
        EnvironmentDTO environmentDTO = environmentService.restore(searchDto);
        return ResponseEntity.ok(environmentDTO);
    }
}
