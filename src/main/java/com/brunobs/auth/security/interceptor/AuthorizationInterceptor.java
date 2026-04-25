package com.brunobs.auth.security.interceptor;

import com.brunobs.auth.authorization.*;
import com.brunobs.auth.context.UserContext;
import com.brunobs.auth.context.UserSession;
import com.brunobs.auth.security.AuthorizationClientService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final AuthorizationClientService authorizationClientService;

    public AuthorizationInterceptor(AuthorizationClientService authorizationClientService
    ) {
        this.authorizationClientService = authorizationClientService;

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
        HttpStatusCode authorize = authorizationClientService.authorize(
                session.getTraceId(),
                session.getTokenJwt(),
                session.getAccountId(),
                session.getEnvironmentId(),
                session.getApplicationId(),
                request.getMethod(),
                level

        );

        if (!authorize.is2xxSuccessful()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not authenticated. Status:" + authorize.value());
            return false;
        }

        return true;
    }
}