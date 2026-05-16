package com.brunobs.auth.security;

import com.brunobs.auth.authorization.AuthorizationLevel;
import com.brunobs.auth.context.UserSession;
import com.brunobs.exception.AccessDeniedException;
import com.brunobs.message.general.GlobalMessages;
import com.brunobs.shared.validation.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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

    public AuthorizationClientService(
            GlobalMessages globalMessages,
            RestClient.Builder builder,
            @Value("${auth.service.url}") String authUrl
    ) {
        this.globalMessages = globalMessages;
        this.restClient = builder.baseUrl(authUrl).build();
        log.info("AuthorizationClientService inicializado com a URL: {}", authUrl);
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
                .uri(uriBuilder -> uriBuilder.path("/authorize").build())
                .headers(h -> {
                    setIfNotNull(h, "X-Correlation-Id", correlationId);
                    setIfNotNull(h, "Authorization", authorization);
                    setIfNotNull(h, "X-Account-Id", account);
                    setIfNotNull(h, "X-Environment", environment);
                    setIfNotNull(h, "X-Application-Id", application);
                    setIfNotNull(h, "X-Method", method);
                    h.setContentType(MediaType.APPLICATION_JSON);
                    if (policy != null) h.set("X-Policy", policy.name());
                })
                .retrieve()
                .onStatus(s -> s.is5xxServerError(), (req, res) -> {
                    throw new HttpServerErrorException(res.getStatusCode());
                })
                .onStatus(s -> s.is4xxClientError(), (req, res) -> {
                    throw new org.springframework.web.client.HttpClientErrorException(res.getStatusCode());
                })
                .body(UserSession.class);
    }

    private void setIfNotNull(HttpHeaders headers, String headerName, Object value) {
        if (value != null) {
            headers.set(headerName, value.toString());
        }
    }

    @Recover
    public UserSession recover(Exception e, String correlationId, String authorization,
                               String account, String environment, String application,
                               String method, AuthorizationLevel policy) {
        log.error("Acesso negado ou falha na comunicação (CorrelationId: {}): {}", correlationId, e.getMessage());

        // Print da stacktrace apenas para debug inicial no seu POC
        e.printStackTrace();

        throw new AccessDeniedException(
                new ValidationResult("headers", globalMessages.userAccessDenaid())
        );
    }
}
