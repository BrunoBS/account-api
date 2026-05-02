package com.brunobs.feature.sharing.participant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AccountSharingDTO {

    @JsonProperty(index = 1)
    private Long accountId;

    @JsonProperty(index = 2)
    private String accountName;

    @JsonProperty(index = 3)
    private List<ApplicationSharingDTO> applications = new ArrayList<>();


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<ApplicationSharingDTO> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationSharingDTO> applications) {
        this.applications = applications;
    }
}