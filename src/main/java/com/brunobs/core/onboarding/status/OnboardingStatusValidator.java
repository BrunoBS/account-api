package com.brunobs.core.onboarding.status;
import com.brunobs.core.catalog.common.BaseTypeValidator;
import com.brunobs.shared.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


@Component
public class OnboardingStatusValidator extends BaseTypeValidator<
        OnboardingStatusEnum,
        OnboardingStatusRepository,
        OnboardingStatusDTO> {

    public OnboardingStatusValidator(OnboardingStatusRepository repository, MessageSource messageSource) {
        super(repository, OnboardingStatusEnum.class, messageSource);
    }

    @Override
    public Long getId(OnboardingStatusDTO dto) {
        return dto.id();
    }

    @Override
    public String getName(OnboardingStatusDTO dto) {
        return dto.name();
    }

    @Override
    public String getLabel(OnboardingStatusDTO dto) {
        return dto.label();
    }

    @Override
    public String getDescription(OnboardingStatusDTO dto) {
        return dto.description();
    }

    @Override
    public OnboardingStatusEnum getEnum(String name) {
        return BaseEnum.from(OnboardingStatusEnum.class, name);
    }
}
