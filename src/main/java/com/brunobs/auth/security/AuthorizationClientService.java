package com.brunobs.auth.security;

import com.brunobs.auth.authorization.AuthorizationLevel;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizationClientService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:2020/authorize";

    @Retryable(
            retryFor = {
                    HttpServerErrorException.class,
                    ResourceAccessException.class
            },
            backoff = @Backoff(delay = 200, multiplier = 2)
    )
    public HttpStatusCode authorize(
            String correlationId,
            String authorization,
            String account,
            String environment,
            String application,
            String method,
            AuthorizationLevel policy
    ) {

        HttpHeaders headers = new HttpHeaders();

        headers.set("X-Correlation-Id", correlationId);
        headers.set("Authorization", authorization);
        headers.set("X-Account-Id", account);
        headers.set("X-Environment", environment);
        headers.set("X-Application-Id", application);
        headers.set("X-Method", method);
        headers.set("X-Policy", policy.name());

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, entity, String.class);

            return response.getStatusCode();

        } catch (HttpClientErrorException e) {
            return e.getStatusCode();
        }
    }
}