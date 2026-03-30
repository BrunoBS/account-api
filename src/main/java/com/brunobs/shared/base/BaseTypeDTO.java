package com.brunobs.shared.base;

import com.fasterxml.jackson.databind.JsonNode;


public interface BaseTypeDTO<T extends BaseTypeDTO<T, ID>, ID> extends BaseDTO<String, ID> {

    ID id();

    String name();

    String label();

    String description();

    Integer sortOrder();

    JsonNode settings();


    T withId(ID id);

    @Override
    default ID registrationIdentifier() {
        return id();
    }

    @Override
    default String registrationName() {
        return name();
    }
}
