package com.brunobs.web.sharing;

import com.brunobs.audit.configs.*;
import com.brunobs.feature.sharing.participant.SharingParticipantService;
import com.brunobs.feature.sharing.participant.dto.*;
import com.brunobs.feature.sharing.participant.repository.projection.SharingDestinationsProjection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/applications/{applicationId}")
public class SharingParticipantController {

    private final SharingParticipantService sharingParticipantService;

    public SharingParticipantController(SharingParticipantService sharingParticipantService) {
        this.sharingParticipantService = sharingParticipantService;
    }


    @GetMapping("/shared-accounts")
    public ResponseEntity<List<AccountSharingDTO>> getAccountsWithApplications() {

        List<AccountSharingDTO> result = sharingParticipantService.getAccountsWithApplications();

        return result.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(result);
    }

    @GetMapping("/shared-destinations")
    public ResponseEntity<List<SharingDestinationsProjection>> findDestinations(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestParam(required = false) String typeFeature,
            @RequestParam(required = false) Long environmentId
    ) {

        List<SharingDestinationsProjection> result =
                sharingParticipantService.findSharingDestinations(
                        accountId,
                        applicationId,
                        environmentId,
                        typeFeature
                );

        return result.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(result);
    }


    @GetMapping("/shared-avalilables")
    public ResponseEntity<List<AvailableSharingDTO>> listSharings(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestParam(required = false) Long targetAccountId,
            @RequestParam(required = false) Long targetApplicationId,
            @RequestParam(required = false) String status
    ) {

        List<AvailableSharingDTO> result =
                sharingParticipantService.findAvailableSharings(
                        accountId,
                        applicationId,
                        targetAccountId,
                        targetApplicationId,
                        status
                );

        return result.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(result);
    }

    @PostMapping("/shared-participants")
    @Auditable(
            entityType = AuditEntityType.SHARING_PARTICIPANT,
            type = AuditEventType.INSERT,
            entity = @AuditField(source = IdSource.RESPONSE, field = "id")
    )
    public ResponseEntity<SharingApplicationDTO> requestSharing(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestBody SharingApplicationRequestDTO request
    ) {

        SharingApplicationDTO response =
                sharingParticipantService.requestSharing(
                        accountId,
                        applicationId,
                        request
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/shared-participants/{participantId}")
    @Auditable(
            entityType = AuditEntityType.SHARING_PARTICIPANT,
            type = AuditEventType.UPDATE,
            entity = @AuditField(source = IdSource.PATH, field = "participantId")
    )
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long participantId,
            @RequestBody SharingApplicationStatusDTO request
    ) {

        sharingParticipantService.updateStatus(
                accountId,
                applicationId,
                participantId,
                request
        );

        return ResponseEntity.noContent().build();
    }
}