package com.brunobs.web.sharing;

import com.brunobs.audit.configs.*;
import com.brunobs.feature.sharing.contract.SharingDTO;
import com.brunobs.feature.sharing.contract.SharingService;
import com.brunobs.feature.sharing.participant.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/applications/{applicationId}/sharings")
public class SharingController {

    private final SharingService sharingService;

    public SharingController(SharingService sharingService) {
        this.sharingService = sharingService;
    }

    @PostMapping
    @Auditable(
            entityType = AuditEntityType.SHARING,
            type = AuditEventType.INSERT,
            entity = @AuditField(source = IdSource.RESPONSE, field = "id")
    )
    public ResponseEntity<SharingDTO> create(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestBody SharingDTO request
    ) {
        SharingDTO response = sharingService.create(
                request.withId(null, accountId, applicationId));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SharingDTO>> findAll(
            @PathVariable Long accountId,
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.ok(sharingService.findAll(accountId, applicationId)
        );
    }


    @GetMapping("/{sharingId}")
    public ResponseEntity<SharingDTO> findById(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId
    ) {

        return ResponseEntity.ok(
                sharingService.findById(
                        accountId,
                        applicationId,
                        sharingId
                )
        );
    }

    @PutMapping("/{sharingId}")
    @Auditable(
            entityType = AuditEntityType.SHARING,
            type = AuditEventType.UPDATE,
            entity = @AuditField(source = IdSource.PATH, field = "sharingId")
    )
    public ResponseEntity<SharingDTO> update(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId,
            @RequestBody SharingDTO request
    ) {
        return ResponseEntity.ok(sharingService.update(request.withId(sharingId, accountId, applicationId))
        );
    }

    @DeleteMapping("/{sharingId}")
    @Auditable(
            entityType = AuditEntityType.SHARING,
            type = AuditEventType.UPDATE,
            entity = @AuditField(source = IdSource.PATH, field = "sharingId")
    )
    public ResponseEntity<Void> delete(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId
    ) {
        sharingService.delete(accountId, applicationId, sharingId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{sharingId}/participants")
    public ResponseEntity<List<SharingOriginByTargetDTO>> findParticipants(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId
    ) {

        return ResponseEntity.ok(
                sharingService.findParticipants(accountId, applicationId, sharingId)
        );
    }


    @PatchMapping("/{sharingId}/participants/{participantId}")
    @Auditable(
            entityType = AuditEntityType.SHARING_PARTICIPANT,
            type = AuditEventType.UPDATE,
            entity = @AuditField(source = IdSource.PATH, field = "participantId")
    )
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId,
            @PathVariable Long participantId,
            @RequestBody SharingApplicationStatusDTO request
    ) {

        sharingService.updateStatus(
                accountId,
                applicationId,
                sharingId,
                participantId,
                request
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{sharingId}/participants/{participantId}")
    @PatchMapping("/{sharingId}/participants/{participantId}")
    @Auditable(
            entityType = AuditEntityType.SHARING_PARTICIPANT,
            type = AuditEventType.DELETE,
            entity = @AuditField(source = IdSource.PATH, field = "participantId")
    )
    public ResponseEntity<Void> removeParticipants(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long sharingId,
            @PathVariable Long participantId
    ) {

        sharingService.removeParticipants(
                accountId,
                applicationId,
                sharingId,
                participantId
        );

        return ResponseEntity.noContent().build();
    }

}
