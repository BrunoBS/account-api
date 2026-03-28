package com.brunobs.config;

import com.brunobs.config.context.UserContext;
import com.brunobs.config.context.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    )  {

        Map<String, String> pathVariables;
        pathVariables = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
        );

        if (pathVariables == null) {
            return true;
        }

        String accountId = pathVariables.get("accountId");
        String applicationId = pathVariables.get("applicationId");

        UserSession session = UserContext.get();

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        boolean hasAccountAccess = session.hasGroup("ACCOUNT_" + accountId);
        boolean hasAppAccess = session.hasGroup("APP_" + applicationId);

        if (!hasAccountAccess || !hasAppAccess) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}