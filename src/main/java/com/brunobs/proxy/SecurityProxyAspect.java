package com.brunobs.proxy;

import com.brunobs.config.context.UserContext;
import com.brunobs.config.context.UserSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Aspect
@Component
public class SecurityProxyAspect {

    // Cache de Patterns para evitar recompilação de Regex (Thread-safe)
    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

    @Around("@annotation(com.brunobs.filter.ProxyAuthorizer)")
    public Object authorize(ProceedingJoinPoint joinPoint, ProxyAuthorizer proxyAuthorizer) throws Throwable {
        Object result = joinPoint.proceed();
        UserSession session = UserContext.get();

        // 1. Curto-circuito para Owners (Performance máxima)
        if (session != null && session.isOwner()) {
            return result;
        }

        if (session == null || session.getGroups() == null) {
            System.out.println("Tentativa de acesso sem sessão ou grupos mapeados.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessão inválida.");
        }

        Set<String> userGroups = session.getGroups();

        // 2. Filtro para Listas/Coleções
        if (result instanceof Collection<?> collection) {
            return collection.stream()
                    .filter(item -> hasAccess(item, userGroups, proxyAuthorizer))
                    .collect(Collectors.toList());
        }

        // 3. Bloqueio para Objeto Único (ex: findById)
        if (result != null && !hasAccess(result, userGroups, proxyAuthorizer)) {
            System.out.printf("Acesso negado. Usuário com grupos {} tentou acessar recurso restrito.", userGroups);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado à conta.");
        }

        return result;
    }

    private boolean hasAccess(Object obj, Set<String> userGroups, ProxyAuthorizer config) {
        if (obj instanceof Authorizable auth) {
            String suffix = auth.getAuthorizerGroup();
            if (suffix == null || suffix.isBlank()) return true;
            String cacheKey = config.prefix() + "-" + suffix;
            Pattern pattern = PATTERN_CACHE.computeIfAbsent(cacheKey, k -> {
                String finalRegex = String.format(config.regexPattern(),
                        Pattern.quote(config.prefix()),
                        Pattern.quote(suffix));
                return Pattern.compile(finalRegex, Pattern.CASE_INSENSITIVE);
            });
            return userGroups.stream().anyMatch(g -> pattern.matcher(g).matches());
        }
        return true;
    }
}
