package com.brunobs.core.catalog.type.infrastructure;

import com.brunobs.shared.base.BaseEnum;


public enum InfrastructureTypeEnum implements BaseEnum<InfrastructureTypeEnum> {

    VM,
    CONTAINER,
    KUBERNETES,
    SERVERLESS;


}
