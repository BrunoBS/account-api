package com.brunobs.auth.authorization;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorizationRequired {

    AuthorizationLevel level() default AuthorizationLevel.OPEN;

}