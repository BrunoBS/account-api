package com.brunobs.auth.security;

import com.brunobs.auth.authorization.AuthorizationMetadataRegistry;
import com.brunobs.auth.authorization.AuthorizationPolicy;
import com.brunobs.auth.context.UserContext;
import com.brunobs.auth.context.UserSession;
import com.brunobs.exception.AccessDeniedException;
import com.brunobs.message.general.GlobalMessages;
import com.brunobs.shared.validation.ValidationResult;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final GlobalMessages globalMessages;
    private final AuthorizationClientService authorizationClientService;
    private final AuthorizationMetadataRegistry authorizationMetadataRegistry;

    public AuthorizationInterceptor(GlobalMessages globalMessages,
                                    AuthorizationClientService authorizationClientService,
                                    AuthorizationMetadataRegistry authorizationMetadataRegistry
    ) {
        this.globalMessages = globalMessages;
        this.authorizationClientService = authorizationClientService;
        this.authorizationMetadataRegistry = authorizationMetadataRegistry;
    }

    @Override
    public boolean preHandle(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull Object handler
    ) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        AuthorizationPolicy policy = authorizationMetadataRegistry.resolve(
                handlerMethod.getBeanType(),
                handlerMethod.getMethod()
        );
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String correlationId = request.getHeader("correlationId");
        String userAgent = request.getHeader("User-Agent");
        String authHeader = request.getHeader("Authorization");
        String accountId = null;
        String environmentId = null;
        String applicationId = null;

        if (correlationId == null || correlationId.isBlank()) {
            throw new AccessDeniedException(new ValidationResult("headers", globalMessages.headersMissing(correlationId)));
        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AccessDeniedException(new ValidationResult("headers", globalMessages.headersMissing(authHeader)));
        }

        if (pathVariables != null) {
            accountId = pathVariables.get("accountId");
            environmentId = pathVariables.get("environmentId");
            applicationId = pathVariables.get("applicationId");
        }


        ResponseEntity<UserSession> sessionResponseEntity = authorizationClientService.authorize(
                correlationId,
                authHeader,
                accountId,
                environmentId,
                applicationId,
                request.getMethod(),
                policy.getLevel()

        );
        if (!sessionResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new AccessDeniedException(new ValidationResult("headers", globalMessages.userAccessDenaid()));
        }
        UserSession body = sessionResponseEntity.getBody();
        UserContext.set(body);

        MDC.put("correlationId", body.getTraceId());
        MDC.put("username", body.getUserName());
        MDC.put("clientIp", request.getRemoteAddr());
        MDC.put("userAgent", userAgent != null ? userAgent : "unknown");
        MDC.put("uri", request.getRequestURI());
        MDC.put("accountId", body.getAccountId());
        MDC.put("environmentId", body.getEnvironmentId());
        MDC.put("applicationId", body.getApplicationId());


        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        MDC.clear(); // limpa tudo
    }
}