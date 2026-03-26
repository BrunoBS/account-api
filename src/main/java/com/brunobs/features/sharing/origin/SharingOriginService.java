package com.brunobs.features.sharing.origin;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.core.catalog.feature.type.FeatureTypeEnum;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeEnum;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeService;
import com.brunobs.exception.ValidationException;
import com.brunobs.features.EntityValidationService;
import com.brunobs.features.sharing.ShareStatusUpdateDTO;
import com.brunobs.features.sharing.target.SharingTarget;
import com.brunobs.features.sharing.target.SharingTargetRepository;
import com.brunobs.features.sharing.target.SharingTargetValidator;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.base.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class SharingOriginService {

    private final SharingOriginMapper sharingOriginMapper;
    private final SharingOriginRepository sharingOriginRepository;
    private final SharingTargetRepository sharingTargetRepository;
    private final SharingTargetValidator validator;
    private final ShareStatusTypeService shareStatusTypeService;
    private final EntityValidationService entityValidationService;

    private final MessageSource messageSource;

    public SharingOriginService(
            SharingOriginMapper sharingOriginMapper,
            SharingOriginRepository sharingOriginRepository, SharingTargetRepository sharingTargetRepository,
            SharingTargetValidator validator,
            ShareStatusTypeService shareStatusTypeService, EntityValidationService entityValidationService,
            MessageSource messageSource) {

        this.sharingOriginMapper = sharingOriginMapper;
        this.sharingOriginRepository = sharingOriginRepository;
        this.sharingTargetRepository = sharingTargetRepository;
        this.validator = validator;
        this.shareStatusTypeService = shareStatusTypeService;
        this.entityValidationService = entityValidationService;
        this.messageSource = messageSource;
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
                    new ValidationResult("SharingOrigin",
                            validator.getMessage(BaseValidator.MSG_NOT_FOUND)));
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
            throw new ValidationException(
                    new ValidationResult("shareStatus",
                            validator.getMessage(BaseTypeValidator.MSG_INVALID_NAME, "shareStatus",
                                    BaseEnum.getOptionsValid(currentStatus.nextStatus().stream().map(Enum::name).toList(), FeatureTypeEnum.class, messageSource))));
        }
        ShareStatusType shareStatusType = shareStatusTypeService.findByName(statusTypeEnum.name());
        sharingOrigin.setShareStatusType(shareStatusType);
        sharingOriginRepository.save(sharingOrigin);
    }

    private ShareStatusTypeEnum getShareStatusTypeEnum(String name) {
        return BaseEnum.from(ShareStatusTypeEnum.class, name);
    }

    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    private SharingOrigin getSharingOrigin(Long id) {
        return sharingOriginRepository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingOrigin",
                                validator.getMessage(BaseValidator.MSG_NOT_FOUND))));
    }


    private SharingTarget getSharingTarget(Long accountId, Long applicationId, Long id, ValidationResult vr) {
        return sharingTargetRepository.findSharing(accountId, applicationId, id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingTarget",
                                validator.getMessage(BaseValidator.MSG_NOT_FOUND))));


    }
}