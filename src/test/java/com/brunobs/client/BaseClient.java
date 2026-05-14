package com.brunobs.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class BaseClient {

    protected final RequestSpecification spec;

    protected BaseClient() {

        this.spec = new RequestSpecBuilder()
                .setContentType("application/json")
                .addHeader("Authorization", "Bearer token-valido")
                .addHeader("correlationId", TestContext.correlationId())
                .build();
    }

    protected BaseClient(RequestSpecification spec) {
        this.spec = spec;
    }
}