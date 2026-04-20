package com.brunobs.auth.context;

public record ParsedGroup(
        String fullGroup,
        String profile,
        String environment,
        String authorizer
) {}