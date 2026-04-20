package com.brunobs.feature.sharing.target;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.common.EnumTypeDTO;
import com.brunobs.core.catalog.feature.type.FeatureType;
import com.brunobs.core.catalog.feature.type.FeatureTypeService;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeType;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeEnum;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeEnum;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeService;
import com.brunobs.exception.ValidationException;
import com.brunobs.feature.EntityValidationService;
import com.brunobs.feature.sharing.ShareStatusUpdateDTO;
import com.brunobs.feature.sharing.origin.SharingOrigin;
import com.brunobs.feature.sharing.origin.SharingOriginDTO;
import com.brunobs.feature.sharing.origin.SharingOriginMapper;
import com.brunobs.feature.sharing.origin.SharingOriginRepository;
import com.brunobs.message.feature.SharingMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SharingTargetService {
    private final EntityValidationService entityValidationService;
    private final FeatureTypeService featureTypeService;
    private final SharingTargetMapper sharingTargetMapper;
    private final SharingOriginMapper sharingOriginMapper;
    private final SharingTargetRepository sharingTargetRepository;
    private final SharingOriginRepository sharingOriginRepository;
    private final SharingTargetValidator validator;
    private final ShareStatusTypeService shareStatusTypeService;
    private final SharingMessages sharingMessages;


    public SharingTargetService(EntityValidationService entityValidationService,
                                FeatureTypeService featureTypeService,
                                SharingTargetMapper sharingTargetMapper,
                                SharingOriginMapper sharingOriginMapper,
                                SharingTargetRepository sharingTargetRepository,
                                SharingOriginRepository sharingOriginRepository,
                                SharingTargetValidator validator,
                                ShareStatusTypeService shareStatusTypeService,
                                SharingMessages sharingMessages) {

        this.entityValidationService = entityValidationService;
        this.featureTypeService = featureTypeService;
        this.sharingTargetMapper = sharingTargetMapper;
        this.sharingOriginMapper = sharingOriginMapper;
        this.sharingTargetRepository = sharingTargetRepository;
        this.sharingOriginRepository = sharingOriginRepository;
        this.validator = validator;
        this.shareStatusTypeService = shareStatusTypeService;
        this.sharingMessages = sharingMessages;
    }

    @Transactional
    public SharingTargetDTO create(SharingTargetDTO dto) {
        validator.validateForCreate(dto);
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(dto.accountId(), dto.applicationId(), vr);
        if (vr.hasErrors()) throw new ValidationException(vr);
        valideApplicationScopeType(application);
        List<FeatureType> features = getFeatures(dto, vr);
        SharingTarget entity = sharingTargetMapper.toEntity(dto, features, application);
        return sharingTargetMapper.toDTO(sharingTargetRepository.save(entity));
    }


    public List<SharingTargetDTO> findAll(Long accountId, Long applicationId) {
        return sharingTargetRepository.findByAccountAndApplication(accountId, applicationId).stream()
                .map(sharingTargetMapper::toDTO).toList();
    }

    public SharingTargetDTO findById(Long accountId, Long applicationId, Long sharingId) {
        return sharingTargetMapper.toDTO(getSharingTarget(accountId, applicationId, sharingId));
    }


    @Transactional
    public SharingTargetDTO update(SharingTargetDTO dto) {
        validator.validateForUpdate(dto);
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(dto.accountId(), dto.applicationId(), vr);
        if (vr.hasErrors()) throw new ValidationException(vr);
        valideApplicationScopeType(application);
        List<FeatureType> features = getFeatures(dto, vr);
        SharingTarget entity = getSharingTarget(dto.accountId(), dto.applicationId(), dto.id());
        sharingTargetMapper.updateEntity(entity, dto, features, application);
        return sharingTargetMapper.toDTO(sharingTargetRepository.save(entity));
    }


    private void valideApplicationScopeType(Application application) {
        ApplicationScopeType applicationScopeType = application.getApplicationScopeType();
        ApplicationScopeTypeEnum applicationScopeTypeEnum = BaseEnum.from(ApplicationScopeTypeEnum.class, applicationScopeType.getName());
        if (!ApplicationScopeTypeEnum.SHARED.equals(applicationScopeTypeEnum)) {
            throw new ValidationException(
                    new ValidationResult("application",
                            sharingMessages.applicationScopeInvalid(ApplicationScopeTypeEnum.SHARED.name())));
        }
    }

    public SharingTarget getSharingTarget(Long accountId, Long applicationId, Long id) {
        return sharingTargetRepository.findSharing(accountId, applicationId, id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingTarget", sharingMessages.getNotFoundTarget())));

    }


    private List<FeatureType> getFeatures(SharingTargetDTO dto, ValidationResult vr) {
        List<EnumTypeDTO> featureNames = dto.features();
        List<String> list = featureNames.stream().map(EnumTypeDTO::name).toList();
        List<FeatureType> featureTypes = featureTypeService.featureTypeList(list);
        Set<String> existingNames = featureTypes.stream()
                .map(FeatureType::getName)
                .collect(Collectors.toSet());
        List<String> missingNames = list.stream()
                .filter(name -> !existingNames.contains(name))
                .toList();
        if (!missingNames.isEmpty()) {
            missingNames.forEach(f -> {
                vr.addError(f, sharingMessages.getNotFoundFeatures());
            });
            return new ArrayList<>();
        }
        return featureTypes;
    }


    @Transactional
    public void deleteTarget(Long accountId, Long applicationId, Long sharingId) {
        SharingTarget target = getSharingTarget(accountId, applicationId, sharingId);
        List<SharingOrigin> origins = sharingOriginRepository.findBySharingTargetId(target.getId());
        origins.forEach(sharingOriginRepository::delete);
        sharingTargetRepository.delete(target);
    }

    @Transactional(readOnly = true)
    public List<SharingOriginDTO> findOrigins(Long accountId, Long applicationId, Long sharingId) {
        SharingTarget target = getSharingTarget(accountId, applicationId, sharingId);
        return sharingOriginRepository.findBySharingTargetId(target.getId())
                .stream().map(sharingOriginMapper::toDTO).toList();
    }

    @Transactional
    public void deleteOrigin(Long accountId, Long applicationId, Long sharingId, Long originId) {
        SharingTarget target = getSharingTarget(accountId, applicationId, sharingId);
        SharingOrigin origin = getSharingOrigin(originId, target);

        sharingOriginRepository.delete(origin);
    }


    @Transactional
    public void updateOriginStatus(Long accountId, Long applicationId, Long sharingIdentifier,
                                   Long originId, ShareStatusUpdateDTO dto) {
        SharingTarget target = getSharingTarget(accountId, applicationId, sharingIdentifier);
        SharingOrigin origin = getSharingOrigin(originId, target);
        String shareStatus = dto == null ? null : dto.shareStatus();

        ShareStatusTypeEnum currentStatus = getShareStatusTypeEnum(origin.getShareStatusType().getName());
        ShareStatusTypeEnum statusTypeEnum = getShareStatusTypeEnum(shareStatus);
        if (!currentStatus.canTransitionTo(statusTypeEnum)) {

            String optionsValid = BaseEnum.getOptionsValid(
                    currentStatus.nextStatus().stream().map(Enum::name).toList(),
                    ShareStatusTypeEnum.class, sharingMessages.getMessageSource()
            );

            throw new ValidationException(
                    new ValidationResult("shareStatus",
                            sharingMessages.statusInvalid(optionsValid)));
        }

        ShareStatusType shareStatusType = shareStatusTypeService.findByName(statusTypeEnum.name());
        origin.setShareStatusType(shareStatusType);
        sharingOriginRepository.save(origin);

    }


    private SharingOrigin getSharingOrigin(Long originId, SharingTarget target) {
        SharingOrigin origin = sharingOriginRepository.findByIdAndSharingTarget(originId, target)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingOrigin", sharingMessages.getNotFoundOrigin())));
        return origin;
    }

    private ShareStatusTypeEnum getShareStatusTypeEnum(String name) {
        return BaseEnum.from(ShareStatusTypeEnum.class, name);
    }

}