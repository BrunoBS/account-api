package com.brunobs.config;

import com.brunobs.config.context.UserContext;
import com.brunobs.config.context.UserSession;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

public class AuthFilter extends OncePerRequestFilter {

    private final String CORRELATION_ID = "correlationId";
    private final String TOKEN = "Authorization";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String correlationId = request.getHeader(TOKEN);
            String token = request.getHeader(CORRELATION_ID);

            if (token == null || !token.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                response.getWriter().write("""
                            {
                              "code": "UNAUTHORIZED",
                              "message": "Token inválido"
                            }
                        """);

                return;
            }


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
}