package com.brunobs.web.publisher;

import com.brunobs.config.security.AuthorizationLevel;
import com.brunobs.config.security.AuthorizationRequired;
import com.brunobs.core.environment.EnvironmentDTO;
import com.brunobs.core.publisher.PublisherDTO;
import com.brunobs.core.publisher.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController {

    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PublisherDTO>> findAll(
            @RequestParam(defaultValue = "true") boolean active,
            @RequestParam(required = false) String scope
    ) {
        return ResponseEntity.ok(service.findAllByStatus(active, scope));
    }

    @PostMapping
    public ResponseEntity<List<PublisherDTO>> create(@RequestBody List<PublisherDTO> dtos) {
        List<PublisherDTO> list = dtos.stream()
                .map(dto -> service.create(dto.withId(null)))
                .toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> update(
            @PathVariable Long id,
            @RequestBody PublisherDTO dto) {
        return ResponseEntity.ok(service.update(dto.withId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/restore")
    @AuthorizationRequired(level = AuthorizationLevel.OWNER)
    public ResponseEntity<PublisherDTO> restore(@PathVariable Long id) {
        PublisherDTO publisherDTO = service.restore(id);
        return ResponseEntity.ok(publisherDTO);
    }
}
