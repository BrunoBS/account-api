package com.brunobs.web.sharing;


import com.brunobs.features.sharing.origin.SharingOriginDTO;
import com.brunobs.features.sharing.origin.SharingOriginService;
import com.brunobs.features.sharing.target.SharingTargetDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/applications/{applicationId}/origins")
public class SharingOriginController {

    private final SharingOriginService service;

    public SharingOriginController(SharingOriginService service) {
        this.service = service;
    }

    // ---------------- CRUD ----------------
    @PostMapping
    public ResponseEntity<SharingOriginDTO> create(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestBody SharingOriginDTO dto) {
        return ResponseEntity.ok(service.create(accountId, applicationId, dto.withId(null)));
    }

    @GetMapping
    public ResponseEntity<List<SharingOriginDTO>> findAll(
            @PathVariable Long accountId,
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(service.findAll(accountId, applicationId));
    }

    @GetMapping("/{originId}")
    public ResponseEntity<SharingOriginDTO> findById(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long originId) {
        return ResponseEntity.ok(service.findById(accountId, applicationId, originId));
    }

    @PutMapping("/{originId}")
    public ResponseEntity<SharingOriginDTO> update(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long originId,
            @RequestBody SharingOriginDTO dto) {
        return ResponseEntity.ok(service.update(accountId, applicationId, dto.withId(originId)));
    }

    @DeleteMapping("/{originId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long originId) {
        service.delete(accountId, applicationId, originId);
        return ResponseEntity.noContent().build();
    }

    // ---------------- Actions (sharings) ----------------
    @GetMapping("/{originId}/sharings")
    public ResponseEntity<List<SharingTargetDTO>> findAllSharings(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long originId) {
        return ResponseEntity.ok(service.findAllSharings(accountId, applicationId, originId));
    }

    @GetMapping("/{originId}/sharings/{sharingId}")
    public ResponseEntity<SharingTargetDTO> findSharingById(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long originId,
            @PathVariable Long sharingId) {
        return ResponseEntity.ok(service.findSharingById(accountId, applicationId, originId, sharingId));
    }

    @PatchMapping("/{originId}/sharings/{sharingId}/action/{status}")
    public ResponseEntity<SharingTargetDTO> performAction(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long originId,
            @PathVariable Long sharingId,
            @PathVariable String status) {
        return ResponseEntity.ok(service.performAction(accountId, applicationId, originId, sharingId, status));
    }
}