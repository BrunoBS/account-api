package com.brunobs.client;

import java.util.UUID;

public class TestContext {

    private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal<>();

    public static String correlationId() {
        if (CORRELATION_ID.get() == null) {
            CORRELATION_ID.set(UUID.randomUUID().toString());
        }
        return CORRELATION_ID.get();
    }

    public static void reset() {
        CORRELATION_ID.remove();
    }
}