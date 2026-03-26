package com.brunobs.audit.configs;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented // Importante para aparecer no JavaDoc
public @interface Auditable {


    String action();

    IdSource source() default IdSource.PATH;

    String field() default "id";

    String description() default "";
}
