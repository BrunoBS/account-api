package com.brunobs.config.security;

import com.brunobs.config.context.UserContext;
import com.brunobs.config.context.UserSession;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final AuthorizationService authorizationService;

    public AuthorizationInterceptor(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
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

        AuthorizationRequired annotation = handlerMethod.getMethodAnnotation(AuthorizationRequired.class);
        AuthorizationLevel level = annotation == null ? AuthorizationLevel.OPEN : annotation.level();
        UserSession session = UserContext.get();
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not authenticated");
            return false;
        }
        authorizationService.checkAuthorization(session, level);
        return true;
    }
}