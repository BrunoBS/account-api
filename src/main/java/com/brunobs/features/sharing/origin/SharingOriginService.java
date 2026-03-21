package com.brunobs.features.sharing.origin;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharingOriginService {

    private final SharingRepository sharingRepository;
    private final SharingOriginRepository originRepository;

    public SharingOriginService(SharingRepository sharingRepository,
                                SharingOriginRepository originRepository) {
        this.sharingRepository = sharingRepository;
        this.originRepository = originRepository;
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
    public List<SharingDTO> findAllSharings(Long accountId, Long applicationId, Long originId) {
        return List.of();
    }

    public SharingDTO findSharingById(Long accountId, Long applicationId, Long originId, Long sharingId) {
        return null;
    }

    public SharingDTO performAction(Long accountId, Long applicationId, Long originId, Long sharingId, String status) {
        // valida permissão e altera status
        return null;
    }
}