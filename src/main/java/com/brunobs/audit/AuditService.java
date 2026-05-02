package com.brunobs.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    private final ObjectMapper objectMapper;
    private final AuditRepository repository;
    private final MessageSource messageSource;

    public AuditService(ObjectMapper objectMapper, AuditRepository repository, MessageSource messageSource) {
        this.objectMapper = objectMapper;
        this.repository = repository;
        this.messageSource = messageSource;
    }

    @Async("auditExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void register(
            String product,
            String origin,
            String username,
            String entityType,
            String eventType,
            String entityId,
            String environmentId,
            int httpStatus,
            String payload
    ) {

        try {

            AuditLog auditLog = new AuditLog();
            auditLog.setCodIdefEnti(entityId);
            auditLog.setCodIdefAmbi(environmentId);
            auditLog.setDatHorEven(Instant.now());
            auditLog.setEntityEvent(entityType);
            auditLog.setMetadataEvent(payload);
            auditLog.setOrigemEvent(origin);
            auditLog.setProductEvent(product);
            auditLog.setTypeEvent(eventType);
            auditLog.setUserEvent(username);

            repository.save(auditLog);

            String successMessage = messageSource.getMessage(
                    "audit.action.success",
                    new Object[]{eventType, username},
                    LocaleContextHolder.getLocale()
            );


            log.info(
                    "Audit log persisted | action={} | data={}",
                    eventType,
                    buildAuditLogJson(auditLog, username, httpStatus, origin, product)
            );
        } catch (Exception e) {
            log.info(
                    "Audit log persisted | action={}  | error={}",
                    eventType,
                    e.getMessage(),
                    e
            );
        }
    }


    private String buildAuditLogJson(
            AuditLog auditLog,
            String username,
            int httpStatus,
            String origin,
            String product
    ) {
        try {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("IdEntidade", auditLog.getCodIdefEnti());
            logMap.put("IdAmbiente", auditLog.getCodIdefAmbi());
            logMap.put("TipoEvento", auditLog.getTypeEvent());
            logMap.put("Entidade", auditLog.getEntityEvent());
            logMap.put("User", username);
            logMap.put("httpStatus", httpStatus);
            logMap.put("API", origin);
            logMap.put("Produto", product);

            return objectMapper.writeValueAsString(logMap);
        } catch (Exception e) {
            return "{}";
        }
    }
}