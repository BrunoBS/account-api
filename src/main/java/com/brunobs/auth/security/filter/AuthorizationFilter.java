package com.brunobs.auth.security.filter;

import com.brunobs.auth.context.UserContext;
import com.brunobs.auth.context.UserSession;
import com.brunobs.auth.security.util.PathVariableUtils;
import com.github.benmanes.caffeine.cache.Cache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class AuthorizationFilter extends OncePerRequestFilter {
    private static final String CORRELATION_ID = "correlationId";
    private static final String AUTHORIZATION = "Authorization";

    private final Cache<String, UserSession> tokenCache;

    public AuthorizationFilter(Cache<String, UserSession> tokenCache) {
        this.tokenCache = tokenCache;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) {

        try {

            final String correlationId = request.getHeader(CORRELATION_ID);
            final String authHeader = request.getHeader(AUTHORIZATION);

            if (correlationId == null || correlationId.isBlank()) {
                unauthorized(response, "Missing correlationId header", "The request must contain 'correlationId'");
                return;
            }

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                unauthorized(response, "Invalid Authorization header", "Bearer token is required");
                return;
            }

            String token = authHeader.substring(7);
            String tokenHash = sha256(token);
            PathVariableUtils ids = PathVariableUtils.extractIds(request);
            UserSession session = tokenCache.get(tokenHash, t -> parseToken(token, ids));

            UserContext.set(session);

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            response.setStatus(401);

        } finally {

            UserContext.clear();

        }
    }

    private UserSession parseToken(String token, PathVariableUtils ids) {

        Claims claims = Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(token)
                .getBody();

        return new UserSession(
                claims.getExpiration().getTime(),
                claims.get("username", String.class),
                claims.get("email", String.class),
                ids.getAccountId(),
                ids.getApplicationId(),
                ids.getEnvironmentId(),
                null,
                new HashSet<>((List<String>) claims.get("groups"))
        );
    }

    private String sha256(String base) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(base.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Erro ao calcular hash SHA-256", e);
        }
    }

    private void unauthorized(HttpServletResponse response, String message, String details) throws IOException {
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