package com.brunobs.web.sharing;

import com.brunobs.features.sharing.ShareStatusUpdateDTO;
import com.brunobs.features.sharing.origin.SharingOriginDTO;
import com.brunobs.features.sharing.target.SharingTargetDTO;
import com.brunobs.features.sharing.target.SharingTargetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/applications/{applicationId}/sharings")
public class SharingTargetController {

    private final SharingTargetService sharingTargetService;

    public SharingTargetController(SharingTargetService sharingTargetService) {
        this.sharingTargetService = sharingTargetService;

    }

    @PostMapping
    public ResponseEntity<SharingTargetDTO> create(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestBody SharingTargetDTO request
    ) {

        SharingTargetDTO response = sharingTargetService.create(
                request.withId(null, accountId, applicationId));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SharingTargetDTO>> findAll(
            @PathVariable Long accountId,
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.ok(sharingTargetService.findAll(accountId, applicationId)
        );
    }

    @GetMapping("/{sharingId}")
    public ResponseEntity<SharingTargetDTO> findById(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId
    ) {
        return ResponseEntity.ok(
                sharingTargetService.findById(accountId, applicationId, sharingId)
        );
    }

    @PutMapping("/{sharingId}")
    public ResponseEntity<SharingTargetDTO> update(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId,
            @RequestBody SharingTargetDTO request
    ) {

        return ResponseEntity.ok(sharingTargetService.update(
                request.withId(sharingId, accountId, applicationId)
                )
        );
    }

    @DeleteMapping("/{sharingId}")
    public ResponseEntity<Void> deleteTarget(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId
    ) {

        sharingTargetService.deleteTarget(accountId, applicationId, sharingId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sharingId}/origins")
    public ResponseEntity<List<SharingOriginDTO>> findOrigins(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId
    ) {

        return ResponseEntity.ok(
                sharingTargetService.findOrigins(accountId, applicationId, sharingId)
        );
    }

    @PatchMapping("/{sharingId}/origins/{originId}")
    public ResponseEntity<Void> updateOriginStatus(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId,
            @PathVariable Long originId,
            @RequestBody ShareStatusUpdateDTO request
    ) {

        sharingTargetService.updateOriginStatus(
                accountId,
                applicationId,
                sharingId,
                originId,
                request
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{sharingIdentifier}/origins/{originId}")
    public ResponseEntity<Void> removeOrigin(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingIdentifier,
            @PathVariable Long originId
    ) {

        sharingTargetService.deleteOrigin(
                accountId,
                applicationId,
                sharingIdentifier,
                originId
        );

        return ResponseEntity.noContent().build();
    }

}