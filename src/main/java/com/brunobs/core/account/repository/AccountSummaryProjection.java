package com.brunobs.core.account.repository;

import com.brunobs.proxy.Authorizable;

public interface AccountSummaryProjection extends Authorizable {
    Long getId();
    String getName();
    String getDescription();
    String getCreatedAt();
    String getType();
    String getIdentifier();
    String getAcronym();
    String getAuthorizerGroup();
    Long getTotalEnvironments();
    Long getTotalPublishers();
    Long getTotalApplications();
}
