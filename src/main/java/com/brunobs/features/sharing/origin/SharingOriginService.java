package com.brunobs.features.sharing.origin;

import com.brunobs.features.sharing.target.SharingTargetDTO;
import com.brunobs.features.sharing.target.SharingTargetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharingOriginService {

    private final SharingTargetRepository sharingTargetRepository;
    private final SharingOriginRepository sharingOriginRepository;

    public SharingOriginService(SharingTargetRepository sharingTargetRepository,
                                SharingOriginRepository sharingOriginRepository) {
        this.sharingTargetRepository = sharingTargetRepository;
        this.sharingOriginRepository = sharingOriginRepository;
    }

    // ---------------- CRUD ----------------
    public SharingOriginDTO create(Long accountId, Long applicationId, SharingOriginDTO dto) {
        return null;
    }

    public List<SharingOriginDTO> findAll(Long accountId, Long applicationId) {
        return List.of();
    }

    public SharingOriginDTO findById(Long accountId, Long applicationId, Long originId) {
        return null;
    }

    public SharingOriginDTO update(Long accountId, Long applicationId, SharingOriginDTO dto) {
        return null;
    }

    public void delete(Long accountId, Long applicationId, Long originId) {
        // lógica de remoção
    }

    // ---------------- Actions (sharings) ----------------
    public List<SharingTargetDTO> findAllSharings(Long accountId, Long applicationId, Long originId) {
        return List.of();
    }

    public SharingTargetDTO findSharingById(Long accountId, Long applicationId, Long originId, Long sharingId) {
        return null;
    }

    public SharingTargetDTO performAction(Long accountId, Long applicationId, Long originId, Long sharingId, String status) {

        return null;
    }
}