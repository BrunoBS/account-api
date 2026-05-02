package com.brunobs.feature.sharing.participant.repository.projection;

public interface SharingOriginByTargetProjection {
    Long getId();
    Long getApplicationId();
    String getApplicationName();
    Long getAccountId();
    String getAccountName();
    String getStatusName();
    String getStatusLabel();
}