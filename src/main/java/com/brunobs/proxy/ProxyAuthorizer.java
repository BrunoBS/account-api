package com.brunobs.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProxyAuthorizer {
    String prefix() default "PM5-";

    String regexPattern() default "^%s.*[-_]%s$";
}

