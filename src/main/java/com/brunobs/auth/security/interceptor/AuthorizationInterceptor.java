package com.brunobs.auth.security.interceptor;

import com.brunobs.auth.authorization.AuthorizationService;
import com.brunobs.auth.context.UserContext;
import com.brunobs.auth.context.UserSession;
import com.brunobs.auth.authorization.AuthorizationPolicy;
import com.brunobs.auth.authorization.AuthorizationPolicyRegistry;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {


    private final AuthorizationService authorizationService;
    private final AuthorizationPolicyRegistry policyRegistry;

    public AuthorizationInterceptor(
            AuthorizationService authorizationService, AuthorizationPolicyRegistry policyRegistry
    ) {
        this.authorizationService = authorizationService;
        this.policyRegistry = policyRegistry;
    }

    @Override
    public boolean preHandle(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull Object handler
    ) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }


        UserSession session = UserContext.get();

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not authenticated");
            return false;
        }
        AuthorizationPolicy policy = policyRegistry.get(handlerMethod);
        authorizationService.checkAuthorization(session, policy.getLevel());

        return true;
    }
}