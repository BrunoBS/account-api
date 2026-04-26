package com.brunobs.auth.messaging.kafka;

public record KafkaPublishRequest(
        String environment,
        String topic,
        Long key,
        String message
) {}