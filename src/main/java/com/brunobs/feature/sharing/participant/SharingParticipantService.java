package com.brunobs.feature.sharing.participant;

import com.brunobs.core.application.Application;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeEnum;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeEnum;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeEnum;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeService;
import com.brunobs.exception.ValidationException;
import com.brunobs.feature.EntityValidationService;
import com.brunobs.feature.sharing.contract.Sharing;
import com.brunobs.feature.sharing.contract.SharingRepository;
import com.brunobs.feature.sharing.participant.dto.*;
import com.brunobs.feature.sharing.participant.dto.AvailableSharingDTO.Destination;
import com.brunobs.feature.sharing.participant.dto.AvailableSharingDTO.Feature;
import com.brunobs.feature.sharing.participant.dto.AvailableSharingDTO.Status;
import com.brunobs.feature.sharing.participant.dto.AvailableSharingDTO.Validation;
import com.brunobs.feature.sharing.participant.repository.SharingParticipantRepository;
import com.brunobs.feature.sharing.participant.repository.projection.AccountsWithAppsProjection;
import com.brunobs.feature.sharing.participant.repository.projection.AvailableSharingProjection;
import com.brunobs.feature.sharing.participant.repository.projection.SharingDestinationsProjection;
import com.brunobs.feature.sharing.participant.repository.projection.SharingOriginProjection;
import com.brunobs.message.feature.SharingMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class SharingParticipantService {

    private final SharingParticipantMapper sharingParticipantMapper;
    private final SharingParticipantRepository sharingParticipantRepository;
    private final SharingRepository sharingRepository;
    private final ShareStatusTypeService shareStatusTypeService;
    private final EntityValidationService entityValidationService;

    private final SharingMessages sharingMessages;

    public SharingParticipantService(
            SharingParticipantMapper sharingParticipantMapper,
            SharingParticipantRepository sharingParticipantRepository,
            SharingRepository sharingRepository,
            ShareStatusTypeService shareStatusTypeService,
            EntityValidationService entityValidationService,
            SharingMessages sharingMessages) {

        this.sharingParticipantMapper = sharingParticipantMapper;
        this.sharingParticipantRepository = sharingParticipantRepository;
        this.sharingRepository = sharingRepository;
        this.shareStatusTypeService = shareStatusTypeService;
        this.entityValidationService = entityValidationService;
        this.sharingMessages = sharingMessages
        ;
    }

    public List<AccountSharingDTO> getAccountsWithApplications() {
        List<AccountsWithAppsProjection> list = sharingParticipantRepository.findAccountsWithApps();
        Map<Long, AccountSharingDTO> map = new LinkedHashMap<>();
        for (AccountsWithAppsProjection item : list) {

            AccountSharingDTO account = map.computeIfAbsent(
                    item.getAccountId(),
                    id -> {
                        AccountSharingDTO dto = new AccountSharingDTO();
                        dto.setAccountId(item.getAccountId());
                        dto.setAccountName(item.getAccountName());
                        return dto;
                    }
            );

            account.getApplications().add(
                    new ApplicationSharingDTO(
                            item.getApplicationId(),
                            item.getApplicationName()
                    )
            );
        }
        return new ArrayList<>(map.values());
    }


    public List<SharingDestinationsProjection> findSharingDestinations(
            Long accountId,
            Long applicationId,
            Long environmentId,
            String typeFeature) {


        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        } else {
            return sharingParticipantRepository.findSharingDestinations(
                    application.getId(),
                    environmentId,
                    typeFeature,
                    ShareStatusTypeEnum.APPROVED.name(),
                    ApplicationScopeTypeEnum.SHARED.name(),
                    FeatureScopeTypeEnum.BACKEND_APPLICATION.name()
            );
        }
    }


    @Transactional(readOnly = true)
    public List<AvailableSharingDTO> findAvailableSharings(
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
            List<AvailableSharingProjection> availableSharings = sharingParticipantRepository.findAvailableSharings(
                    idApplicationTarget,
                    idAccountTarget,
                    application.getId(),
                    statusTypeEnum == null ? null : statusTypeEnum.name());
            return mapAvailableSharingDTO(availableSharings);
        }
    }


    @Transactional
    public SharingApplicationDTO requestSharing(
            Long accountId,
            Long applicationId,
            SharingApplicationRequestDTO sharingApplicationRequestDTO) {
        ValidationResult vr = new ValidationResult();

        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        Sharing sharing = getSharingTarget(
                sharingApplicationRequestDTO.accountSharingId(),
                sharingApplicationRequestDTO.applicationSharingId(), sharingApplicationRequestDTO.sharingId());

        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        } else {
            if (applicationId.equals(sharingApplicationRequestDTO.applicationSharingId())) {
                throw new ValidationException(
                        new ValidationResult("sharingOrigin",
                                sharingMessages.sharingDestinstionSame()));
            }
            findByIdAndSharingTarget(application.getId(), sharing);
            ShareStatusType shareStatusType = shareStatusTypeService.findByName(ShareStatusTypeEnum.WAITING_DESTINATION_APPROVAL.name());
            SharingParticipant entity = sharingParticipantMapper.toEntity(sharing, shareStatusType, application);
            SharingParticipant save = sharingParticipantRepository.save(entity);
            return sharingParticipantMapper.toDTO(save);

        }

    }

    public SharingOriginProjection findByIdOriginApplication(
            Long accountId,
            Long applicationId,
            Long originId) {
        List<SharingOriginProjection> list = findAOriginApplication(accountId, applicationId, originId);
        Optional<SharingOriginProjection> o = list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
        if (o.isEmpty() || o.get().getIdSharingOrigin() == null) {
            throw new ValidationException(
                    new ValidationResult("SharingOrigin", sharingMessages.getNotFoundOrigin()));
        }
        return o.get();
    }

    private List<SharingOriginProjection> findAOriginApplication(
            Long accountId,
            Long applicationId,
            Long originId) {
        ValidationResult vr = new ValidationResult();
        Application application = entityValidationService.validateApplication(accountId, applicationId, vr);
        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        } else {
            return sharingParticipantRepository.findSharingOrigins(application.getId(), originId, null, null, null);
        }
    }

    @Transactional
    public void updateStatus(
            Long accountId,
            Long applicationId,
            Long sharingId,
            Long participantId,
            SharingApplicationStatusDTO dto
    ) {
        validateSharing(accountId, applicationId, sharingId);
        SharingParticipant participant = getSharingOrigin(participantId);
        validateParticipantBelongsToSharing(participant, sharingId);
        applyStatusChange(participant, applicationId, dto);
    }

    @Transactional
    public void updateStatus(
            Long accountId,
            Long applicationId,
            Long participantId,
            SharingApplicationStatusDTO dto
    ) {
        ValidationResult vr = new ValidationResult();
        entityValidationService.validateApplication(accountId, applicationId, vr);
        if (vr.hasErrors()) {
            throw new ValidationException(vr);
        }

        SharingParticipant participant = getSharingOrigin(participantId);
        if (!participant.getApplication().getId().equals(applicationId)) {
            throw new ValidationException(
                    new ValidationResult("participant", "Participante não pertence à aplicação informada")
            );
        }

        applyStatusChange(participant, applicationId, dto);
    }

    private void applyStatusChange(
            SharingParticipant participant,
            Long applicationId,
            SharingApplicationStatusDTO dto
    ) {

        ShareStatusTypeEnum currentStatus = getShareStatusTypeEnum(participant.getShareStatusType().getName());
        ShareStatusTypeEnum newStatus = getShareStatusTypeEnum(dto.shareStatus());

        if (newStatus == null) {
            throw new ValidationException(new ValidationResult("shareStatus", "Status inválido"));
        }

        if (currentStatus == newStatus) {
            throw new ValidationException(new ValidationResult(
                    "shareStatus",
                    "O compartilhamento já está com o status '" + currentStatus.name() + "'.")
            );
        }

        if (currentStatus.nextStatus().isEmpty()) {
            throw new ValidationException(new ValidationResult("shareStatus", "Status atual não permite alteração")
            );
        }

        validateActor(currentStatus.allowedActor(), applicationId, participant);
        if (!currentStatus.canTransitionTo(newStatus)) {

            String optionsValid = BaseEnum.getOptionsValid(
                    currentStatus.nextStatus().stream().map(Enum::name).toList(),
                    ShareStatusTypeEnum.class,
                    "- ",
                    sharingMessages.getMessageSource()
            );

            throw new ValidationException(
                    new ValidationResult("shareStatus",
                            sharingMessages.statusInvalid(optionsValid))
            );
        }

        ShareStatusType status = shareStatusTypeService.findByName(newStatus.name());
        participant.setShareStatusType(status);
        sharingParticipantRepository.save(participant);
    }

    private ShareStatusTypeEnum getShareStatusTypeEnum(String name) {
        return BaseEnum.from(ShareStatusTypeEnum.class, name);
    }

    private SharingParticipant getSharingOrigin(Long id) {
        return sharingParticipantRepository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingOrigin",
                                sharingMessages.getNotFoundOrigin())));
    }

    private void findByIdAndSharingTarget(Long id, Sharing target) {
        if (sharingParticipantRepository.existsByIdAndSharing(id, target)) {
            throw new ValidationException(
                    new ValidationResult("sharingOrigin",
                            sharingMessages.sharingDuplicated()));
        }
    }


    private Sharing getSharingTarget(Long accountId, Long applicationId, Long id) {
        return sharingRepository.findSharing(accountId, applicationId, id)
                .orElseThrow(() -> new ValidationException(
                        new ValidationResult("sharingTarget",
                                sharingMessages.getNotFoundTarget())));


    }

    private void validateSharing(Long accountId, Long applicationId, Long sharingId) {
        getSharingTarget(accountId, applicationId, sharingId);
    }

    private List<AvailableSharingDTO> mapAvailableSharingDTO(
            List<AvailableSharingProjection> list) {
        return list.stream().map(p -> {

            List<Feature> features = Arrays.stream(
                            Optional.ofNullable(p.getFeatures()).orElse("").split(",")
                    )
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(label -> new Feature(
                            label.toUpperCase().replace(" ", "_"),
                            label
                    ))
                    .toList();

            boolean isValid = "VALID".equals(p.getValidationStatus());

            List<AvailableSharingDTO.Error> errors = isValid
                    ? List.of()
                    : Arrays.stream(p.getValidationStatus().split(","))
                    .map(String::trim)
                    .map(name -> new AvailableSharingDTO.Error(
                            name,
                            mapErrorLabel(name)
                    ))
                    .toList();

            // 🔹 DESTINATION
            Destination destination = new Destination(
                    p.getApplicationDestinationId(),
                    p.getApplicationDestinationName(),
                    p.getAccountDestinationId(),
                    p.getAccountDestinationName()
            );

            // 🔹 STATUS
            Status status = new Status(
                    p.getShareStatusName(),
                    p.getShareStatusLabel()
            );

            return new AvailableSharingDTO(
                    p.getSharingId(),
                    p.getSharingName(),
                    destination,
                    features,
                    status,
                    new Validation(isValid, errors)
            );

        }).toList();
    }

    private void validateParticipantBelongsToSharing(
            SharingParticipant participant,
            Long sharingId
    ) {
        if (!participant.getSharingTarget().getId().equals(sharingId)) {
            throw new ValidationException(
                    new ValidationResult("sharing",
                            "Participante não pertence ao compartilhamento informado")
            );
        }
        if (!participant.getSharingTarget().getId().equals(sharingId)) {
            throw new ValidationException(
                    new ValidationResult("sharing",
                            "Participante não pertence ao compartilhamento informado")
            );
        }
    }

    private void validateActor(
            ShareStatusTypeEnum.ShareActor actor,
            Long applicationId,
            SharingParticipant participant
    ) {

        if (actor == null) {
            throw new ValidationException(
                    new ValidationResult("shareStatus", "Estado não permite alteração")
            );
        }

        Long originAppId = participant.getApplication().getId();
        Long destinationAppId = participant.getSharingTarget().getApplication().getId();

        boolean isAllowed = switch (actor) {
            case ORIGIN -> applicationId.equals(originAppId);
            case DESTINATION -> applicationId.equals(destinationAppId);
            case BOTH -> applicationId.equals(originAppId) || applicationId.equals(destinationAppId);
        };

        if (!isAllowed) {
            throw new ValidationException(
                    new ValidationResult("sharing",
                            "Usuário não tem permissão para alterar este status")
            );
        }
    }


    private String mapErrorLabel(String name) {
        return switch (name) {
            case "DESTINATION_NOT_FOUND" -> "Destination application not found";
            case "DESTINATION_INACTIVE" -> "Destination application inactive";
            case "DESTINATION_ACCOUNT_NOT_FOUND" -> "Destination account not found";
            case "DESTINATION_ACCOUNT_INACTIVE" -> "Destination account inactive";
            case "DESTINATION_SCOPE_NOT_FOUND" -> "Destination scope not found";
            case "DESTINATION_NOT_SHARED" -> "Destination not shared";
            case "ORIGIN_NOT_FOUND" -> "Origin application not found";
            case "ORIGIN_INACTIVE" -> "Origin application inactive";
            case "ORIGIN_ACCOUNT_NOT_FOUND" -> "Origin account not found";
            case "ORIGIN_ACCOUNT_INACTIVE" -> "Origin account inactive";
            case "ORIGIN_SCOPE_NOT_FOUND" -> "Origin scope not found";
            case "ORIGIN_NOT_BACKEND" -> "Origin not backend";
            default -> name;
        };

    }
}
