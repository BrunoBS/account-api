package com.brunobs.core.catalog.type.authorization;

import com.brunobs.shared.base.BaseEnum;


public enum AuthorizationTypeEnum implements BaseEnum<AuthorizationTypeEnum> {

    DEV(1),

    TST(2),

    ADM(3);

    private final int order;

    AuthorizationTypeEnum(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
