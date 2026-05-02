package com.brunobs.audit.configs;

import java.lang.annotation.*;

@Repeatable(Auditables.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {

    AuditEntityType entityType();

    AuditEventType type();

    AuditField entity();

    AuditField environment() default @AuditField;

}