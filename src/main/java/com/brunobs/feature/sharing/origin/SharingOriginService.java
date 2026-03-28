package com.brunobs.feature.sharing.origin;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeEnum;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeService;
import com.brunobs.exception.ValidationException;
import com.brunobs.feature.EntityValidationService;
import com.brunobs.feature.sharing.ShareStatusUpdateDTO;
import com.brunobs.feature.sharing.target.SharingTarget;
import com.brunobs.feature.sharing.target.SharingTargetRepository;
import com.brunobs.message.feature.SharingMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class SharingOriginService {

    private final SharingOriginMapper sharingOriginMapper;
    private final SharingOriginRepository sharingOriginRepository;
    private final SharingTargetRepository sharingTargetRepository;
    private final ShareStatusTypeService shareStatusTypeService;
    private final EntityValidationService entityValidationService;

    private final SharingMessages sharingMessages;

    public SharingOriginService(
            SharingOriginMapper sharingOriginMapper,
            SharingOriginRepository sharingOriginRepository,
            SharingTargetRepository sharingTargetRepository,
            ShareStatusTypeService shareStatusTypeService,
            EntityValidationService entityValidationService,
            SharingMessages sharingMessages) {

        this.sharingOriginMapper = sharingOriginMapper;
        this.sharingOriginRepository = sharingOriginRepository;
        this.sharingTargetRepository = sharingTargetRepository;
        this.shareStatusTypeService = shareStatusTypeService;
        this.entityValidationService = entityValidationService;
        this.sharingMessages = sharingMessages
        ;
    }


    @Transactional(readOnly = true)
    public List<SharingOriginProjection> findAvailableSharings(
            Long accountId,
            Long applicationId,
            Long idAccountTarget,
            Long idApplicationTarget,
            String shareStatusType
    ) {
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        ShareStatusTypeEnum statusTypeEnum = BaseEnum.from(ShareStatusTypeEnum.class, shareStatusType);
        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        } else {
            return sharingOriginRepository.findAvailableSharings(
                    application.getId(),
                    idAccountTarget,
                    idApplicationTarget,
                    statusTypeEnum == null ? null : statusTypeEnum.name());
        }
    }


    public SharingOriginDTO requestSharing(Long accountId, Long applicationId, SharingOriginRequestDTO sharingOriginRequestDTO) {
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        SharingTarget sharingTarget = getSharingTarget(
                sharingOriginRequestDTO.accountTargetId(),
                sharingOriginRequestDTO.applicationTargetId(), sharingOriginRequestDTO.accountTargetId(), vr);
        //TODO:  valida se o  compartilhamento ja foi realizado entre a aplicacao e o target.
        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        } else {
            ShareStatusType shareStatusType = shareStatusTypeService.findByName(ShareStatusTypeEnum.WAITING_DESTINATION_APPROVAL.name());
            SharingOrigin entity = sharingOriginMapper.toEntity(sharingTarget, shareStatusType, application);
            SharingOrigin save = sharingOriginRepository.save(entity);
            return sharingOriginMapper.toDTO(save);

        }

    }

    public SharingOriginProjection findByIdOriginApplication(Long accountId, Long applicationId, Long originId) {
        List<SharingOriginProjection> list = findAOriginApplication(accountId, applicationId, originId);
        Optional<SharingOriginProjection> o = list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
        if (o.isEmpty() || o.get().getIdSharingOrigin() == null) {
            throw new ValidationException(
                    new ValidationResult("SharingOrigin", sharingMessages.getNotFoundOrigin()));
        }
        return o.get();
    }

    public List<SharingOriginProjection> findAllOriginApplication(Long accountId, Long applicationId) {
        return findAOriginApplication(accountId, applicationId, null);
    }

    private List<SharingOriginProjection> findAOriginApplication(Long accountId, Long applicationId, Long originId) {
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        } else {

            return sharingOriginRepository.findSharingOrigins(originId, applicationId, null, null, null);
        }
    }


    public void delete(Long accountId, Long applicationId, Long originId) {
        SharingOriginProjection sharingOriginProjection = findByIdOriginApplication(accountId, applicationId, originId);
        sharingOriginRepository.deleteById(sharingOriginProjection.getIdSharingOrigin());
    }

    public void updateStatus(Long accountId, Long applicationId, Long originId, ShareStatusUpdateDTO dto) {
        SharingOriginProjection sharingOriginProjection = findByIdOriginApplication(accountId, applicationId, originId);
        SharingOrigin sharingOrigin = getSharingOrigin(sharingOriginProjection.getIdSharingOrigin());
        String shareStatus = dto == null ? null : dto.shareStatus();
        ShareStatusTypeEnum currentStatus = getShareStatusTypeEnum(sharingOrigin.getShareStatusType().getName());
        ShareStatusTypeEnum statusTypeEnum = getShareStatusTypeEnum(shareStatus);
        if (!currentStatus.canTransitionTo(statusTypeEnum)) {
            String optionsValid = BaseEnum.getOptionsValid(
                    currentStatus.nextStatus().stream().map(Enum::name).toList(),
                    ShareStatusTypeEnum.class, sharingMessages.getMessageSource()
            );
            throw new ValidationException(
                    new ValidationResult("shareStatus", sharingMessages.statusInvalid(optionsValid)));
        }
        ShareStatusType shareStatusType = shareStatusTypeService.findByName(statusTypeEnum.name());
        sharingOrigin.setShareStatusType(shareStatusType);
        sharingOriginRepository.save(sharingOrigin);
    }

    private ShareStatusTypeEnum getShareStatusTypeEnum(String name) {
        return BaseEnum.from(ShareStatusTypeEnum.class, name);
    }

    private SharingOrigin getSharingOrigin(Long id) {
        return sharingOriginRepository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingOrigin",
                                sharingMessages.getNotFoundOrigin())));
    }


    private SharingTarget getSharingTarget(Long accountId, Long applicationId, Long id, ValidationResult vr) {
        return sharingTargetRepository.findSharing(accountId, applicationId, id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingTarget",
                                sharingMessages.getNotFoundTarget())));


    }
}