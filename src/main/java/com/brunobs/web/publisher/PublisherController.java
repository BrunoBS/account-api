package com.brunobs.web.publisher;

import com.brunobs.core.publisher.PublisherDTO; // PublicadorDTO -> PublisherDTO
import com.brunobs.core.publisher.PublisherService; // ProviderService -> PublisherService
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
            @RequestParam(defaultValue = "true") boolean active) {
        return ResponseEntity.ok(service.findAllByStatus(active));
    }

    @PostMapping
    public ResponseEntity<PublisherDTO> create(@RequestBody PublisherDTO dto) {
        return ResponseEntity.ok(service.create(dto.withId(null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> update(
            @PathVariable Long id,
            @RequestBody PublisherDTO dto) {
        return ResponseEntity.ok(service.update(dto.withId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
