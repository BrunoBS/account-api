package com.brunobs.proxy;

import com.brunobs.auth.context.UserContext;
import com.brunobs.auth.context.UserSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Set;

@Aspect
@Component
public class SecurityProxyAspect {

    private static final Logger log = LoggerFactory.getLogger(SecurityProxyAspect.class);

    @Around("@annotation(proxyAuthorizer)")
    public Object authorize(ProceedingJoinPoint joinPoint, ProxyAuthorizer proxyAuthorizer) throws Throwable {
        System.out.println("passei aqui");
        UserSession session = UserContext.get();
        System.out.println("passei aqui");
        if (session != null && session.isOwner()) {
            log.debug("Usuário OWNER ignorou validação de autorização.");
            return joinPoint.proceed();
        }

        if (session == null || session.getGroups() == null || session.getGroups().isEmpty()) {
            log.warn("Tentativa de acesso sem sessão ou grupos mapeados.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessão inválida.");
        }

        Object result = joinPoint.proceed();

        Set<String> userGroups = session.getGroups();

        if (result instanceof Collection<?> collection) {
            return collection.stream()
                    .filter(item -> hasAccess(item, session))
                    .toList();
        }

        if (result != null && !hasAccess(result, session)) {

            log.warn(
                    "ACCESS DENIED → gruposUsuario={} recurso={}",
                    userGroups,
                    result.getClass().getSimpleName()
            );

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado ao recurso.");
        }

        return result;
    }

    private boolean hasAccess(Object obj, UserSession session) {
        if (obj instanceof Authorizable auth) {
            return session.hasAuthorizer(auth.getAuthorizerGroup());
        }
        return true;
    }
}