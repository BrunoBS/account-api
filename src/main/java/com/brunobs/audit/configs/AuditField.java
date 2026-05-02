package com.brunobs.audit.configs;

public @interface AuditField {

    IdSource source() default  IdSource.PATH;
    String field() default  "";
}