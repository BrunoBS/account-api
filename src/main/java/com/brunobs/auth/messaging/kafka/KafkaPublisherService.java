package com.brunobs.auth.messaging.kafka;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaPublisherService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:6060/kafka/publish";

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void publish(KafkaPublishRequest request) {

        restTemplate.postForEntity(
                url,
                request,
                String.class
        );

        System.out.println("Mensagem enviada para Kafka");
    }

    @Recover
    public void recover(Exception ex, KafkaPublishRequest request) {

        System.out.println("Falha ao enviar mensagem após várias tentativas");
        System.out.println("Mensagem perdida: " + request);
    }
}