package com.brunobs.web.environment; // ambiente -> environment

import com.brunobs.core.environment.EnvironmentService; // AmbienteService -> EnvironmentService
import com.brunobs.core.environment.EnvironmentDTO; // AmbienteDTO -> EnvironmentDTO
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
    public EnvironmentDTO create(@RequestBody EnvironmentDTO request) {
        // withId(null, null) segue o padrão de imutabilidade dos Records em inglês
        return environmentService.create(request.withId(null, null));
    }

    @GetMapping
    public List<EnvironmentDTO> findAll(
            @RequestParam(defaultValue = "true") boolean active) { // situacao -> active
        return environmentService.findAllDefault(active);
    }

    @GetMapping("/{id}")
    public EnvironmentDTO findById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") boolean active) {
        EnvironmentDTO searchDto = EnvironmentDTO.of(null, id, active);
        return environmentService.findBy(searchDto);
    }

    @PutMapping("/{id}")
    public EnvironmentDTO update(
            @PathVariable Long id,
            @RequestBody EnvironmentDTO request) {
        return environmentService.update(request.withId(id, null));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        EnvironmentDTO searchDto = EnvironmentDTO.of(null, id, true);
        environmentService.delete(searchDto);
    }
}
