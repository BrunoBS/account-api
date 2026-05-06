package com.brunobs.auth.security;

import com.brunobs.auth.authorization.AuthorizationLevel;
import com.brunobs.auth.context.UserSession;
import com.brunobs.exception.AccessDeniedException;
import com.brunobs.message.general.GlobalMessages;
import com.brunobs.shared.validation.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Service
public class AuthorizationClientService {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationClientService.class);

    private final GlobalMessages globalMessages;
    private final RestClient restClient;

    public AuthorizationClientService(GlobalMessages globalMessages, RestClient.Builder builder) {
        this.globalMessages = globalMessages;
        this.restClient = builder.baseUrl("http://localhost:2020").build();
    }

    @Retryable(
            retryFor = {HttpServerErrorException.class, ResourceAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 200, multiplier = 2)
    )
    public UserSession authorize(
            String correlationId, String authorization, String account,
            String environment, String application, String method,
            AuthorizationLevel policy
    ) {
        return restClient.post()
                .uri("/authorize")
                .headers(h -> {
                    h.set("X-Correlation-Id", correlationId);
                    h.set("Authorization", authorization);
                    h.set("X-Account-Id", account);
                    h.set("X-Environment", environment);
                    h.set("X-Application-Id", application);
                    h.set("X-Method", method);
                    h.set("X-Policy", policy.name());
                    h.setContentType(MediaType.APPLICATION_JSON);
                })
                .retrieve()
                // Se for 5xx, lança a exceção que dispara o Retry
                .onStatus(s -> s.is5xxServerError(), (req, res) -> {
                    throw new HttpServerErrorException(res.getStatusCode());
                })
                // Se for 4xx (como 403), lança exceção que cai direto no @Recover (sem retry)
                .onStatus(s -> s.is4xxClientError(), (req, res) -> {
                    throw new org.springframework.web.client.HttpClientErrorException(res.getStatusCode());
                })
                .body(UserSession.class);
    }

    @Recover
    public UserSession recover(Exception e, String correlationId, String authorization,
                               String account, String environment, String application,
                               String method, AuthorizationLevel policy) {
        log.error("Acesso negado ou falha na comunicação (CorrelationId: {}): {}", correlationId, e.getMessage());

        // Agora, qualquer erro (4xx, 5xx após retries ou Timeout) resulta na sua exceção de negócio
        throw new AccessDeniedException(
                new ValidationResult("headers", globalMessages.userAccessDenaid())
        );
    }
}
