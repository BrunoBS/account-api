package com.brunobs.feature.sharing.participant.repository.projection;

public interface AccountsWithAppsProjection {

    Long getAccountId();
    String getAccountName();
    Long getApplicationId();
    String getApplicationName();
}