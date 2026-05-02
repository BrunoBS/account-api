package com.brunobs.feature.sharing.participant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationSharingDTO {

    @JsonProperty(index = 1)
    private Long applicationId;

    @JsonProperty(index = 2)
    private String applicationName;

    public ApplicationSharingDTO(Long applicationId, String applicationName) {
        this.applicationId = applicationId;
        this.applicationName = applicationName;
    }


    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}