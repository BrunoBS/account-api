package com.brunobs.audit.configs;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented // Importante para aparecer no JavaDoc
public @interface Auditable {

    /**
     * A ação sendo executada (ex: "USER_CREATE", "ORDER_UPDATE").
     */
    String action();

    /**
     * Define de onde o ID deve ser extraído (PATH, BODY, HEADER).
     */
    IdSource source() default IdSource.BODY;

    /**
     * O nome do campo que contém o identificador.
     * Se vazio, pode-se assumir um padrão como "id".
     */
    String field() default "id";

    /**
     * Descrição opcional para contexto adicional na trilha de auditoria.
     */
    String description() default "";
}
