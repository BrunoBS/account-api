package com.brunobs.auth.authorization;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthorizationMetadataRegistry implements SmartInitializingSingleton {

    private final ApplicationContext applicationContext;

    private final Map<String, AuthorizationPolicy> registry = new ConcurrentHashMap<>();
    public AuthorizationMetadataRegistry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);
        controllers.values().forEach(this::register);
    }

    private void register(Object bean) {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        if (targetClass.getPackageName().startsWith("org.springframework")) {
            return;
        }

        for (Method method : targetClass.getMethods()) {
            Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            AuthorizationRequired methodAnn = AnnotatedElementUtils.findMergedAnnotation(specificMethod, AuthorizationRequired.class);
            AuthorizationRequired classAnn = AnnotatedElementUtils.findMergedAnnotation(targetClass, AuthorizationRequired.class);
            AuthorizationPolicy policy = buildPolicy(methodAnn, classAnn);
            String key = buildKey(targetClass, specificMethod);
            registry.put(key, policy);
        }
    }

    public AuthorizationPolicy resolve(Class<?> targetClass, Method method) {
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        String key = buildKey(targetClass, specificMethod);
        return registry.getOrDefault(key, AuthorizationPolicy.open());
    }

    private String buildKey(Class<?> clazz, Method method) {
        String params = String.join(",", Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).toArray(String[]::new));
        return String.format("%s#%s(%s)", clazz.getName(), method.getName(), params);
    }


    private AuthorizationPolicy buildPolicy(
            AuthorizationRequired methodAnn,
            AuthorizationRequired classAnn
    ) {

        if (methodAnn != null) {
            return new AuthorizationPolicy(
                    methodAnn.level(),
                    AuthorizationPolicy.Source.METHOD
            );
        }

        if (classAnn != null) {
            return new AuthorizationPolicy(
                    classAnn.level(),
                    AuthorizationPolicy.Source.CLASS
            );
        }

        return AuthorizationPolicy.open();
    }
}