package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.shared.BaseEnum;


public enum ShareStatusTypeEnum implements BaseEnum<ShareStatusTypeEnum> {

    WAITING_DESTINATION_APPROVAL,

    APPROVED,

    REJECTED,

    WAITING_SOURCE_APPROVAL;


}
