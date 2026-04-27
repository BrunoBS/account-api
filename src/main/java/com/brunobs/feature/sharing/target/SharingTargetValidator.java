package com.brunobs.feature.sharing.target;


import com.brunobs.core.catalog.common.EnumTypeDTO;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeEnum;
import com.brunobs.core.catalog.feature.type.FeatureTypeEnum;
import com.brunobs.message.feature.SharingMessages;
import com.brunobs.shared.base.BaseEnum;
import com.brunobs.shared.base.BaseValidator;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SharingTargetValidator extends BaseValidator<SharingTargetDTO, Long> {

    private final SharingTargetRepository repository;
    private final SharingMessages sharingMessages;


    public SharingTargetValidator(SharingTargetRepository repository, SharingMessages sharingMessages) {
        this.repository = repository;
        this.sharingMessages = sharingMessages;
    }

    @Override
    protected void validateAttributes(SharingTargetDTO dto, ValidationResult vr) {
        if (dto.name() == null || dto.name().isBlank()) {
            vr.addError("name", sharingMessages.nameRequired());
        } else if (dto.name().length() < 3 || dto.name().length() > 100) {
            vr.addError("name", sharingMessages.nameInvalid(3, 100));
        }

        if (dto.description() == null || dto.description().isBlank()) {
            vr.addError("description", sharingMessages.descriptionRequired());
        } else if (dto.description().length() < 3 || dto.description().length() > 100) {
            vr.addError("description", sharingMessages.descriptionInvalid(3, 100));
        }

        List<FeatureTypeEnum> allowedFeatures = FeatureScopeTypeEnum.BACKEND_APPLICATION.getAllowedFeatureTypes();
        int index = 0;

        if (dto.features() == null || dto.features().isEmpty()) {
            vr.addError("features", sharingMessages.featuresRequired());
            for (EnumTypeDTO featureName : dto.features()) {
                String path = "features[" + index + "]";
                FeatureTypeEnum featureTypeEnum = getFeatureTypeEnum(featureName.name());
                if (featureTypeEnum == null || !allowedFeatures.contains(featureTypeEnum)) {
                    vr.addError(path, sharingMessages.featuresInvalid(getListOr(allowedFeatures, sharingMessages.getMessageSource())));
                }
            }
        }
        validateAdditionalFields(dto, vr);
    }

    public String recordRequired() {
        return sharingMessages.getNotFoundTarget();
    }

    @Override
    protected void validateIntegrity(SharingTargetDTO dto, ValidationResult vr) {
        Long id = dto.id() == null ? 0L : dto.id();
        Long applicationId = dto.applicationId() == null ? 0L : dto.applicationId();
        if (repository.existsByNameIgnoreCaseAndApplicationIdAndIdNot(dto.name(), applicationId, id)) {
            vr.addError("name", sharingMessages.duplicateName(dto.name()));
        }
    }

    @Override
    public String entityName() {
        return SharingTarget.class.getSimpleName();
    }

    private FeatureTypeEnum getFeatureTypeEnum(String name) {
        return BaseEnum.from(FeatureTypeEnum.class, name);
    }
}
