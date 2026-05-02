package com.brunobs.feature.sharing.participant.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface AvailableSharingProjection {

    @JsonProperty(index = 1)
    Long getSharingId();

    @JsonProperty(index = 2)
    String getSharingName();

    @JsonProperty(index = 3)
    String getFeatures();

    @JsonProperty(index = 4)
    Long getApplicationDestinationId();

    @JsonProperty(index = 5)
    String getApplicationDestinationName();

    @JsonProperty(index = 6)
    String getStatusDestination();

    @JsonProperty(index = 7)
    Long getAccountDestinationId();

    @JsonProperty(index = 8)
    String getAccountDestinationName();

    @JsonProperty(index = 9)
    String getShareStatusLabel();

    @JsonProperty(index = 10)
    String getShareStatusName();

    @JsonProperty(index = 11)
    String getValidationStatus();


}