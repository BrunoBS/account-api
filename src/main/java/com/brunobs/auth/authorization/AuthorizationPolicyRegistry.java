package com.brunobs.auth.authorization;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationPolicyRegistry {

    private final Map<HandlerMethod, AuthorizationPolicy> cache = new HashMap<>();
    public void register(HandlerMethod method, AuthorizationPolicy policy) {
        cache.put(method, policy);
    }
    public AuthorizationPolicy get(HandlerMethod method) {
        return cache.getOrDefault(method, AuthorizationPolicy.open());
    }
}