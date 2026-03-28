package com.brunobs.web.sharing;

import com.brunobs.feature.sharing.ShareStatusUpdateDTO;
import com.brunobs.feature.sharing.origin.SharingOriginDTO;
import com.brunobs.feature.sharing.origin.SharingOriginProjection;
import com.brunobs.feature.sharing.origin.SharingOriginRequestDTO;
import com.brunobs.feature.sharing.origin.SharingOriginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/applications/{applicationId}")
public class SharingOriginController {

    private final SharingOriginService sharingOriginService;

    public SharingOriginController(SharingOriginService sharingOriginService) {
        this.sharingOriginService = sharingOriginService;
    }

    @GetMapping("/sharings-origins")
    public ResponseEntity<List<SharingOriginProjection>> listAvailableSharings(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestParam (required = false) Long idAccountTarget,
            @RequestParam (required = false) Long idApplicationTarget,
            @RequestParam (required = false) String shareStatusType
    ) {

        List<SharingOriginProjection> allOriginApplication =
                sharingOriginService.findAvailableSharings(
                        accountId,
                        applicationId,
                        idAccountTarget,
                        idApplicationTarget,
                        shareStatusType
                );
        return allOriginApplication.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(allOriginApplication);

    }

    @PostMapping("/origins")
    public ResponseEntity<SharingOriginDTO> requestSharing(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @RequestBody SharingOriginRequestDTO sharingOriginRequestDTO
    ) {
        SharingOriginDTO response = sharingOriginService.requestSharing(
                accountId,
                applicationId,
                sharingOriginRequestDTO
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/origins")
    public ResponseEntity<List<SharingOriginProjection>> listOrigins(
            @PathVariable Long accountId,
            @PathVariable Long applicationId
    ) {

        List<SharingOriginProjection> allOriginApplication = sharingOriginService.
                findAllOriginApplication(accountId, applicationId);

        return allOriginApplication.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(allOriginApplication);
    }

    @GetMapping("/origins/{originId}")
    public ResponseEntity<SharingOriginProjection> findOrigin(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long originId
    ) {
        SharingOriginProjection sharingOriginProjection =
                sharingOriginService.findByIdOriginApplication(accountId, applicationId, originId);
        return ResponseEntity.ok(sharingOriginProjection);

    }

    @PatchMapping("/origins/{originId}")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long originId,
            @RequestBody ShareStatusUpdateDTO request
    ) {

        sharingOriginService.updateStatus(
                accountId,
                applicationId,
                originId,
                request
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/origins/{originId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long accountId,
            @PathVariable Long applicationId,
            @PathVariable Long originId
    ) {

        sharingOriginService.delete(accountId, applicationId, originId);

        return ResponseEntity.noContent().build();
    }

}