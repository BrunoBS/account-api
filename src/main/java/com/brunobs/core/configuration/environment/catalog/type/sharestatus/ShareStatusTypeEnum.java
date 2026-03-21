package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.shared.BaseEnum;

/**
 * Enum representing the states of a sharing process.
 * Standardizes Source (Origin) and Destination (Target) terminology.
 */
public enum ShareStatusTypeEnum implements BaseEnum<ShareStatusTypeEnum> {

    /** Waiting for the receiver (Target) to approve */
    WAITING_DESTINATION_APPROVAL,

    /** Sharing process completed and approved */
    APPROVED,

    /** Sharing process rejected by either party */
    REJECTED,

    /** Waiting for the sender (Origin) to approve */
    WAITING_SOURCE_APPROVAL;


}
