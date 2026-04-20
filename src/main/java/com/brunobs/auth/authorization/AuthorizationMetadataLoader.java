package com.brunobs.auth.authorization;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationMetadataLoader implements ApplicationRunner {

    private final RequestMappingHandlerMapping handlerMapping;

    private final Map<HandlerMethod, AuthorizationPolicy> registry = new HashMap<>();

    public AuthorizationMetadataLoader(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void run(ApplicationArguments args) {
        handlerMapping.getHandlerMethods().forEach((mapping, method) -> {

            AuthorizationPolicy policy = resolvePolicy(method);

            registry.put(method, policy);
        });
    }

    private AuthorizationPolicy resolvePolicy(HandlerMethod handlerMethod) {

        Method method = handlerMethod.getMethod();
        Class<?> clazz = handlerMethod.getBeanType();

        AuthorizationRequired methodAnn = method.getAnnotation(AuthorizationRequired.class);

        if (methodAnn != null) {
            return new AuthorizationPolicy(
                    methodAnn.level(),
                    AuthorizationPolicy.Source.METHOD
            );
        }

        AuthorizationRequired classAnn =
                clazz.getAnnotation(AuthorizationRequired.class);

        if (classAnn != null) {
            return new AuthorizationPolicy(
                    classAnn.level(),
                    AuthorizationPolicy.Source.CLASS
            );
        }

        return new AuthorizationPolicy(
                AuthorizationLevel.OPEN,
                AuthorizationPolicy.Source.DEFAULT
        );
    }

    public AuthorizationPolicy get(HandlerMethod method) {
        return registry.get(method);
    }
}