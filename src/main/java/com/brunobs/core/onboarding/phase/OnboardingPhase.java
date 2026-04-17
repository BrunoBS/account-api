package com.brunobs.core.onboarding.phase;

import com.brunobs.core.catalog.common.BaseType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "type_onboardings")
public class OnboardingPhase extends BaseType {
    @Column(name = "orientation", nullable = false, length = 255)
    protected String orientation;

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}
