package com.brunobs.web.sharing;


import com.brunobs.features.sharing.origin.SharingOriginDTO;
import com.brunobs.features.sharing.target.SharingTargetDTO;
import com.brunobs.features.sharing.target.SharingTargetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/applications/{applicationId}/sharings")
public class SharingTargetController {

    private final SharingTargetService service;

    public SharingTargetController(SharingTargetService service) {
        this.service = service;
    }

    // ---------------- CRUD ----------------
    @PostMapping
    public ResponseEntity<SharingTargetDTO> create(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestBody SharingTargetDTO dto) {
        return ResponseEntity.ok(service.create(dto.withId(null, accountId, applicationId)));
    }

    @GetMapping
    public ResponseEntity<List<SharingTargetDTO>> findAll(
            @PathVariable Long accountId,
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(service.findAll(accountId, applicationId));
    }

    @GetMapping("/{sharingId}")
    public ResponseEntity<SharingTargetDTO> findById(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId) {
        return ResponseEntity.ok(service.findById(accountId, applicationId, sharingId));
    }

    @PutMapping("/{sharingId}")
    public ResponseEntity<SharingTargetDTO> update(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId,
            @RequestBody SharingTargetDTO dto) {
        return ResponseEntity.ok(service.update(dto.withId(sharingId, accountId, applicationId)));
    }

    @DeleteMapping("/{sharingId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId) {
        service.action(accountId, applicationId, sharingId);
        return ResponseEntity.noContent().build();
    }

    // ---------------- Actions (origins) ----------------
    @GetMapping("/{sharingId}/origins")
    public ResponseEntity<List<SharingOriginDTO>> findAllOrigins(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId) {
        return ResponseEntity.ok(service.findAllOrigins(accountId, applicationId, sharingId));
    }

    @GetMapping("/{sharingId}/origins/{originId}")
    public ResponseEntity<SharingOriginDTO> findOriginById(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId,
            @PathVariable Long originId) {
        return ResponseEntity.ok(service.findOriginById(accountId, applicationId, sharingId, originId));
    }

    @PatchMapping("/{sharingId}/origins/{originId}/action/{status}")
    public ResponseEntity<SharingOriginDTO> performAction(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId,
            @PathVariable Long originId,
            @PathVariable String status) {
        return ResponseEntity.ok(service.performAction(accountId, applicationId, sharingId, originId, status));
    }

    @DeleteMapping("/{sharingId}/origins/{originId}")
    public ResponseEntity<Void> deleteOriginById(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId,
            @PathVariable Long originId) {
        service.deleteOriginById(accountId, applicationId, sharingId, originId);
        return ResponseEntity.noContent().build();
    }
}