package com.brunobs.config.security;

import com.brunobs.config.context.UserSession;
import com.brunobs.config.security.repository.AuthorizationRepository;
import com.brunobs.config.security.repository.AuthorizationResult;
import com.brunobs.core.catalog.type.authorization.AuthorizationTypeEnum;
import com.brunobs.exception.AccessDeniedException;
import com.brunobs.message.general.GlobalMessages;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;
    private final GlobalMessages globalMessages;

    public AuthorizationService(AuthorizationRepository authorizationRepository,
                                GlobalMessages globalMessages) {
        this.authorizationRepository = authorizationRepository;
        this.globalMessages = globalMessages;
    }

    public void checkAuthorization(UserSession session, AuthorizationLevel level) {
        if (session == null) {
            throw new AccessDeniedException(
                    new ValidationResult("authentication", globalMessages.userSessionNotFound()));
        }

        AuthorizationResult authorization = authorizationRepository.findAuthorization(
                session.getAccountId(),
                session.getApplicationId(),
                session.getEnvironmentId(),
                level.name()
        );

        if (authorization == null
                || !StringUtils.hasText(authorization.getAuthorizerGroup())
                || !StringUtils.hasText(authorization.getName())
                || !StringUtils.hasText(authorization.getSigla())) {
            throw new AccessDeniedException(
                    new ValidationResult("authentication", globalMessages.userGroupsNotFound()));
        }

        if (!isUserAuthorized(session, authorization)) {
            throw new AccessDeniedException(
                    new ValidationResult("authentication", globalMessages.userGroupMissing()));
        }
    }

    /**
     * Verifica se o usuário possui grupo suficiente para acessar a rota
     *
     */
    private boolean isUserAuthorized(UserSession session, AuthorizationResult authorization) {
        String expectedLevel = authorization.getName(); // DEV, TST, ADM

        return session.getGroups().stream()
                .anyMatch(userGroup -> {
                    if (!userGroup.endsWith("_" + authorization.getAuthorizerGroup())) {
                        return false;
                    }

                    String[] parts = userGroup.split("_", 2);
                    if (parts.length < 2) return false;
                    String userLevel = parts[0].substring(parts[0].lastIndexOf("-") + 1);
                    AuthorizationTypeEnum userEnum = AuthorizationTypeEnum.valueOf(userLevel);
                    AuthorizationTypeEnum expectedEnum = AuthorizationTypeEnum.valueOf(expectedLevel);
                    return userEnum.getOrder() >= expectedEnum.getOrder();
                });
    }

}