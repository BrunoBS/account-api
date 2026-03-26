package com.brunobs.features.sharing.origin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SharingOriginProjection {

    @JsonProperty(index = 1)
    Long getIdSharingOrigin();

    @JsonProperty(index = 2)
    Long getIdSharingTarget();

    @JsonProperty(index = 2)
    String getSharingName();

    @JsonProperty(index = 3)
    String getSharingDescription();

    @JsonProperty(index = 4)
    Long getTargetAccountId();

    @JsonProperty(index = 5)
    String getTargetAccountName();

    @JsonProperty(index = 6)
    Long getApplicationTargetId();

    @JsonProperty(index = 7)
    String getApplicationTargetName();


    @JsonProperty(index = 8)
    String getShareStatusLabel();

    @JsonProperty(index = 9)
    String getShareStatusName();

}