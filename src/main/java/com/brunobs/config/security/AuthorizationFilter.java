package com.brunobs.config.security;

import com.brunobs.config.context.UserContext;
import com.brunobs.config.context.UserSession;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(1)
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID = "correlationId";
    private static final String ACCOUNT_ID = "accountId";
    private static final String APPLICATION_ID = "applicationId";
    private static final String ENVIRONMENT_ID = "environmentId";
    private static final String AUTHORIZATION = "Authorization";


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            final String correlationId = request.getHeader(CORRELATION_ID);
            final String authorization = request.getHeader(AUTHORIZATION);


            if (correlationId == null || correlationId.isBlank()) {
                unauthorized(response, "Missing correlationId header",
                        "The request must contain the header 'correlationId'");
                return;
            }

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                unauthorized(response, "Invalid Authorization header",
                        "Authorization header with Bearer token is required");
                return;
            }

            String userId = "bruno.barbosa";
            Set<String> groups = Set.of(
                    "PM5_ORWER",
                    "PM5-BBS-ADM_FINANCEIRO_PM5",
                    "PM5-BBS-DEV_FINANCEIRO_PM5"
            );
            PathVariableUtils.PathIds ids = PathVariableUtils.extractIds(request);
            String accountId = ids.getAccountId();
            String environmentId = ids.getEnvironmentId();
            String applicationId = ids.getApplicationId();


            UserContext.set(new UserSession(userId,
                    accountId,
                    applicationId,
                    environmentId,
                    correlationId,
                    groups));

            filterChain.doFilter(request, response);

        } finally {
            UserContext.clear();
        }
    }

    private void unauthorized(
            HttpServletResponse response,
            String message,
            String details
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("""
                {
                  "code": "UNAUTHORIZED",
                  "message": "%s",
                  "details": "%s"
                }
                """.formatted(message, details));
    }
}