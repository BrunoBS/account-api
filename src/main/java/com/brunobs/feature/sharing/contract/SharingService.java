package com.brunobs.feature.sharing.contract;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.common.EnumTypeDTO;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeEnum;
import com.brunobs.core.catalog.feature.type.FeatureType;
import com.brunobs.core.catalog.feature.type.FeatureTypeService;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeType;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeEnum;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeEnum;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeService;
import com.brunobs.exception.ValidationException;
import com.brunobs.feature.EntityValidationService;
import com.brunobs.feature.sharing.participant.SharingParticipantService;
import com.brunobs.feature.sharing.participant.dto.SharingApplicationStatusDTO;
import com.brunobs.feature.sharing.participant.SharingParticipant;
import com.brunobs.feature.sharing.participant.SharingParticipantMapper;
import com.brunobs.feature.sharing.participant.repository.SharingParticipantRepository;
import com.brunobs.feature.sharing.participant.dto.SharingOriginByTargetDTO;
import com.brunobs.feature.sharing.participant.repository.projection.SharingOriginByTargetProjection;
import com.brunobs.message.feature.SharingMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.base.StatusDTO;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SharingService {
    private final EntityValidationService entityValidationService;
    private final FeatureTypeService featureTypeService;
    private final SharingMapper sharingMapper;
    private final SharingRepository sharingRepository;
    private final SharingParticipantRepository sharingParticipantRepository;
    private final SharingValidator validator;
    private final ShareStatusTypeService shareStatusTypeService;
    private final SharingMessages sharingMessages;
    private final SharingParticipantService sharingParticipantService;


    public SharingService(EntityValidationService entityValidationService,
                          FeatureTypeService featureTypeService,
                          SharingMapper sharingMapper,
                          SharingRepository sharingRepository,
                          SharingParticipantRepository sharingParticipantRepository,
                          SharingValidator validator,
                          ShareStatusTypeService shareStatusTypeService,
                          SharingMessages sharingMessages, SharingParticipantService sharingParticipantService) {

        this.entityValidationService = entityValidationService;
        this.featureTypeService = featureTypeService;
        this.sharingMapper = sharingMapper;

        this.sharingRepository = sharingRepository;
        this.sharingParticipantRepository = sharingParticipantRepository;
        this.validator = validator;
        this.shareStatusTypeService = shareStatusTypeService;
        this.sharingMessages = sharingMessages;
        this.sharingParticipantService = sharingParticipantService;
    }

    @Transactional
    public SharingDTO create(SharingDTO dto) {
        validator.validateForCreate(dto);
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(dto.accountId(), dto.applicationId(), vr);
        List<FeatureType> features = getFeatures(dto, application, vr);
        SharingDTO hashFeature = getHashFeature(dto, application, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);
        valideApplicationScopeType(application);
        Sharing entity = sharingMapper.toEntity(hashFeature, features, application);
        return sharingMapper.toDTO(sharingRepository.save(entity));
    }

    @Transactional
    public List<SharingDTO> findAll(Long accountId, Long applicationId) {
        return sharingRepository.findByAccountAndApplication(accountId, applicationId).stream()
                .map(sharingMapper::toDTO).toList();
    }

    @Transactional
    public SharingDTO findById(Long accountId, Long applicationId, Long sharingId) {
        return sharingMapper.toDTO(getSharingTarget(accountId, applicationId, sharingId));
    }

    @Transactional
    public SharingDTO update(SharingDTO dto) {
        validator.validateForUpdate(dto);
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(dto.accountId(), dto.applicationId(), vr);
        List<FeatureType> features = getFeatures(dto, application, vr);
        SharingDTO hashFeature = getHashFeature(dto, application, vr);
        if (vr.hasErrors()) throw new ValidationException(vr);
        valideApplicationScopeType(application);
        Sharing entity = getSharingTarget(hashFeature.accountId(), hashFeature.applicationId(), hashFeature.id());
        updateParticipantsStatusIfFeaturesChanged(entity, hashFeature.hashFeatures());
        sharingMapper.updateEntity(entity, hashFeature, features, application);
        return sharingMapper.toDTO(sharingRepository.save(entity));
    }

    @Transactional
    public void delete(Long accountId, Long applicationId, Long sharingId) {
        Sharing sharing = getSharingTarget(accountId, applicationId, sharingId);
        List<SharingParticipant> participants = sharingParticipantRepository.findBySharing(sharing);
        participants.forEach(sharingParticipantRepository::delete);
        sharingRepository.delete(sharing);
    }

    @Transactional(readOnly = true)
    public List<SharingOriginByTargetDTO> findParticipants(Long accountId, Long applicationId, Long sharingId) {
        Sharing target = getSharingTarget(accountId, applicationId, sharingId);
        return sharingParticipantRepository.findBySharingId(target.getId())
                .stream()
                .map(p -> new SharingOriginByTargetDTO(
                        p.getId(),
                        p.getApplicationId(),
                        p.getApplicationName(),
                        p.getAccountId(),
                        p.getAccountName(),
                        new StatusDTO(
                                p.getStatusName(),
                                p.getStatusLabel()
                        )
                ))
                .toList();
    }

    @Transactional
    public void updateStatus(Long accountId, Long applicationId, Long sharingIdentifier,
                             Long originId, SharingApplicationStatusDTO dto) {
        sharingParticipantService.updateStatus(accountId,applicationId, sharingIdentifier, originId, dto);

    }


    @Transactional
    public void removeParticipants(Long accountId, Long applicationId, Long sharingId, Long originId) {
        Sharing target = getSharingTarget(accountId, applicationId, sharingId);
        SharingParticipant origin = getSharingOrigin(originId, target);
        sharingParticipantRepository.delete(origin);
    }

    private Sharing getSharingTarget(Long accountId, Long applicationId, Long id) {
        return sharingRepository.findSharing(accountId, applicationId, id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingTarget", sharingMessages.getNotFoundTarget())));
    }

    private SharingParticipant getSharingOrigin(Long originId, Sharing target) {
        return sharingParticipantRepository.findByIdAndSharing(originId, target)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingOrigin", sharingMessages.getNotFoundOrigin())));
    }

    private ShareStatusTypeEnum getShareStatusTypeEnum(String name) {
        return BaseEnum.from(ShareStatusTypeEnum.class, name);
    }

    private SharingDTO getHashFeature(SharingDTO dto, Application application, ValidationResult vr) {
        Long id = dto.id() == null ? 0L : dto.id();
        List<EnumTypeDTO> featureNames = dto.features();
        List<String> list = featureNames.stream().map(EnumTypeDTO::name).toList();
        String hash = SharingHashUtil.generateHash(application.getId(), list);
        if (sharingRepository.existsByHashFeaturesAndApplicationIdAndIdNot(hash, application.getId(), id)) {
            String collect = dto.features().stream().map(EnumTypeDTO::label).collect(Collectors.joining(","));
            vr.addError("features", sharingMessages.duplicateFeatures(collect));
        }
        return dto.withHashFeature(hash);
    }

    private List<FeatureType> getFeatures(SharingDTO dto, Application applicationa, ValidationResult vr) {
        List<EnumTypeDTO> featureNames = dto.features();
        List<String> list = featureNames.stream().map(EnumTypeDTO::name).toList();
        Set<FeatureType> featureTypes = featureTypeService.featureTypeList(list).stream().filter(
                f ->
                        f.getFeatureScope().getName().equals(FeatureScopeTypeEnum.BACKEND_APPLICATION.name())
        ).collect(Collectors.toSet());
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
        return featureTypes.stream().toList();
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


    private void updateParticipantsStatusIfFeaturesChanged(
            Sharing entity,
            String newHashFeatures
    ) {
        if (entity.getHashFeatures().equals(newHashFeatures)) {
            return;
        }
        List<SharingParticipant> participants = sharingParticipantRepository.findBySharing(entity);
        if (participants.isEmpty()) {
            return;
        }
        ShareStatusType waitingSource = shareStatusTypeService.findByName(ShareStatusTypeEnum.WAITING_SOURCE_APPROVAL.name());
        participants.stream()
                .filter(p ->ShareStatusTypeEnum.APPROVED.name().equals(p.getShareStatusType().getName()))
                .forEach(p -> p.setShareStatusType(waitingSource));
        sharingParticipantRepository.saveAll(participants);
    }


}