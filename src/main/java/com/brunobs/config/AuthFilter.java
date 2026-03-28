package com.brunobs.config;

import com.brunobs.config.context.UserContext;
import com.brunobs.config.context.UserSession;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@Order(1)
public class AuthFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID = "correlationId";
    private static final String AUTHORIZATION = "Authorization";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String correlationId = request.getHeader(CORRELATION_ID);
            String authorization = request.getHeader(AUTHORIZATION);

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

            String token = authorization.substring(7);

            // aqui normalmente você validaria o JWT

            String userId = "bruno.barbosa";

            Set<String> groups = Set.of(
                    "ACCOUNT_10",
                    "APP_20"
            );

            UserContext.set(new UserSession(userId, correlationId, groups));

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