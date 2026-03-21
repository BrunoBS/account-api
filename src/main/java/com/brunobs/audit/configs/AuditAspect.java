package com.brunobs.audit.configs;

import com.brunobs.audit.AuditService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;

@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    private final Environment env;
    private final HttpServletRequest request;

    public AuditAspect(AuditService auditService,
                       ObjectMapper objectMapper,
                       Environment env,
                       HttpServletRequest request) {
        this.auditService = auditService;
        this.objectMapper = objectMapper;
        this.env = env;
        this.request = request;
    }

    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint pjp, Auditable auditable) throws Throwable {
        String systemName = env.getProperty("audit.system.name", "DEFAULT_SYSTEM");

        // Executa o método da API
        Object result = pjp.proceed();

        // Guideline: Só audita se o retorno for ResponseEntity (padrão REST) e sucesso (2xx)
        if (result instanceof ResponseEntity<?> response && response.getStatusCode().is2xxSuccessful()) {

            Object id = extractId(pjp, response.getBody(), auditable);
            String responseJson = objectMapper.writeValueAsString(response.getBody());
            String user = getAuthenticatedUser();

            auditService.register(
                    systemName,
                    user,
                    auditable.action(),
                    id,
                    response.getStatusCode().value(),
                    responseJson
            );
        }

        return result;
    }

    private Object extractId(ProceedingJoinPoint pjp, Object responseBody, Auditable auditable) {
        String fieldName = auditable.field();

        return switch (auditable.source()) {
            case PATH -> extractFromPath(pjp, fieldName);
            case BODY -> extractFromBody(pjp, fieldName);
            case HEADER -> request.getHeader(fieldName);
            case RESPONSE -> extractFromObject(responseBody, fieldName);
        };
    }

    private Object extractFromPath(ProceedingJoinPoint pjp, String fieldName) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = pjp.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(fieldName)) {
                return args[i];
            }
        }
        return null;
    }

    private Object extractFromBody(ProceedingJoinPoint pjp, String fieldName) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        Object[] args = pjp.getArgs();

        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof RequestBody) {
                    return extractFromObject(args[i], fieldName);
                }
            }
        }
        return null;
    }

    /**
     * Usa Jackson para extrair o valor.
     * Vantagem: Funciona com Records, Classes e respeita @JsonProperty.
     */
    private Object extractFromObject(Object obj, String fieldName) {
        if (obj == null || fieldName == null || fieldName.isEmpty()) return null;

        try {
            JsonNode node = objectMapper.valueToTree(obj);
            JsonNode value = node.get(fieldName);
            return (value != null && !value.isNull()) ? value.asText() : null;
        } catch (Exception e) {
            return null; // Em um guideline, decida se quer logar erro de extração aqui
        }
    }

    private String getAuthenticatedUser() {
        // TODO: Integrar com SecurityContextHolder.getContext().getAuthentication()
        return "anonymous";
    }
}
