package com.brunobs.config.context;

public record ParsedGroup(
        String profile,
        String environment,
        String authorizer
) {}