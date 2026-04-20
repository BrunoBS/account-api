package com.brunobs.auth.messaging.dto;

public record KafkaPublishRequest(
        String environment,
        String topic,
        Long key,
        String message
) {}