package com.brunobs.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);
    private final AuditRepository repository;
    private final MessageSource messageSource;

    public AuditService(AuditRepository repository, MessageSource messageSource) {
        this.repository = repository;
        this.messageSource = messageSource;
    }

    @Async("auditExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void register(
            String system,
            String username,
            String action,
            Object id,
            int httpStatus,
            String payload) {

        try {
            String entityId = (id != null) ? id.toString() : null;

            /*AuditLog auditLog = new AuditLog(
                    system,
                    username,
                    action,
                    entityId,
                    httpStatus,
                    payload
            );*/

   //         repository.save(auditLog);


            String successMessage = messageSource.getMessage(
                    "audit.action.success",
                    new Object[]{action, username},
                    LocaleContextHolder.getLocale()
            );
            log.info("Audit log saved: {} | System={} | EntityId={}", successMessage, system, entityId);

        } catch (Exception e) {
            log.error("Critical: Failed to persist audit log for action: {} - Error: {}", action, e.getMessage());
        }
    }
}
