package com.brunobs.audit;

import com.brunobs.audit.configs.AuditField;
import com.brunobs.audit.configs.AuditProperties;
import com.brunobs.audit.configs.Auditable;
import com.brunobs.auth.context.UserContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

@Aspect
@Component
public class AuditAspect {

    public static final String MESSAGE_NO_CONTENT = "{\"message\":\"NO_CONTENT\"}";
    public static final String ENVIRONMENT_DEFAULT = "0";

    private final AuditProperties auditProperties;
    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    public AuditAspect(AuditProperties auditProperties,
                       AuditService auditService,
                       ObjectMapper objectMapper,
                       HttpServletRequest request) {
        this.auditProperties = auditProperties;
        this.auditService = auditService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Around("@annotation(com.brunobs.audit.configs.Auditable)")
    public Object audit(ProceedingJoinPoint pjp) throws Throwable {

        Object result = pjp.proceed();

        if (!(result instanceof ResponseEntity<?> response)) {
            return result;
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            return result;
        }

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Auditable[] auditables = method.getAnnotationsByType(Auditable.class);

        Object responseBody = response.getBody();
        String user = getAuthenticatedUser();

        for (Auditable auditable : auditables) {


            if (responseBody instanceof Collection<?> collection) {

                for (Object item : collection) {
                    processItem(pjp, response.getStatusCode().value(), auditable, item, user);
                }
            } else
                processItem(pjp, response.getStatusCode().value(), auditable, responseBody, user);

        }

        return result;
    }

    private Object resolveByAnnotation(
            ProceedingJoinPoint pjp,
            Object responseBody,
            AuditField config
    ) {
        if (config == null) return null;
        String fieldName = config.field();

        return switch (config.source()) {

            case PATH -> extractFromPath(pjp, fieldName);

            case BODY -> extractFromObject(extractRequestBody(pjp), fieldName);

            case RESPONSE -> extractFromObject(responseBody, fieldName);

            case HEADER -> request.getHeader(fieldName);
        };
    }


    private void processItem(
            ProceedingJoinPoint pjp,
            int statusCode,
            Auditable auditable,
            Object item,
            String user
    ) {

        String entityId = stringify(resolveByAnnotation(pjp, item, auditable.entity()));
        String environment = stringify(resolveByAnnotation(pjp, item, auditable.environment()));

        if (environment == null) {
            environment = ENVIRONMENT_DEFAULT;
        }

        if (entityId == null || entityId.isBlank()) {
            return;
        }

        String payload = safePayload(item);
        try {
            auditService.register(
                    auditProperties.getProduct(),
                    auditProperties.getOrigin(),
                    user,
                    auditable.entityType().name(),
                    auditable.type().name(),
                    entityId,
                    environment,
                    statusCode,
                    payload
            );
        } catch (Exception e) {
            System.err.println("AUDIT_FAIL: " + e.getMessage());
        }
    }

    private Object extractRequestBody(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Annotation[][] annotations = signature.getMethod().getParameterAnnotations();
        Object[] args = pjp.getArgs();

        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof RequestBody) {
                    return args[i];
                }
            }
        }
        return null;
    }

    private Object extractFromPath(ProceedingJoinPoint pjp, String fieldName) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String[] names = signature.getParameterNames();
        Object[] args = pjp.getArgs();

        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(fieldName)) {
                return args[i];
            }
        }
        return null;
    }

    private Object extractFromObject(Object obj, String fieldName) {
        if (obj == null || fieldName == null || fieldName.isEmpty()) return null;

        try {
            JsonNode node = objectMapper.valueToTree(obj);
            JsonNode value = node.get(fieldName);
            return (value != null && !value.isNull()) ? value.asText() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String getAuthenticatedUser() {
        return UserContext.get().getUserName();
    }

    private String safePayload(Object body) {
        if (body == null) return MESSAGE_NO_CONTENT;

        try {
            return objectMapper.writeValueAsString(body);
        } catch (Exception e) {
            return "{\"message\":\"PAYLOAD_SERIALIZATION_ERROR\"}";
        }
    }

    private String stringify(Object value) {
        return value != null ? value.toString() : null;
    }
}