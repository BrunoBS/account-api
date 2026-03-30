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

import java.util.Objects;

@Service
public class AuthorizationService {
    private static final String ADMIN_PORTAL_GROUP = "PM5_ORWER";
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
        if (session.getGroups().contains(ADMIN_PORTAL_GROUP) || AuthorizationLevel.OPEN.equals(level)) {
            return;
        }

        if (AuthorizationLevel.OWNER.equals(level) && !session.hasGroup(ADMIN_PORTAL_GROUP)) {
            throw new AccessDeniedException(
                    new ValidationResult("authentication", globalMessages.userNotOwner()));
        }

        AuthorizationResult authResult = authorizationRepository.findAuthorization(
                session.getAccountId(),
                session.getApplicationId(),
                session.getEnvironmentId(),
                level.name()
        );

        if (

                isInvalidResult(authResult)) {
            throw new AccessDeniedException(
                    new ValidationResult("authentication", globalMessages.userGroupsNotFound()));
        }


        if (session.getGroups().

                contains(ADMIN_PORTAL_GROUP)) {
            return;
        }

        if (!

                isUserAuthorized(session, authResult)) {
            throw new AccessDeniedException(
                    new ValidationResult("authentication", globalMessages.userGroupMissing()));
        }
    }

    private boolean isInvalidResult(AuthorizationResult result) {
        return result == null
                || !StringUtils.hasText(result.getAuthorizerGroup())
                || !StringUtils.hasText(result.getName());
    }

    private boolean isUserAuthorized(UserSession session, AuthorizationResult authResult) {
        try {
            AuthorizationTypeEnum requiredLevel = AuthorizationTypeEnum.valueOf(authResult.getName());
            String suffix = "_" + authResult.getAuthorizerGroup();
            return session.getGroups().stream()
                    .filter(group -> group.endsWith(suffix))
                    .map(this::tryExtractLevel)
                    .filter(Objects::nonNull)
                    .anyMatch(userLevel -> userLevel.getOrder() >= requiredLevel.getOrder());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private AuthorizationTypeEnum tryExtractLevel(String group) {
        try {
            int start = group.lastIndexOf("-") + 1;
            int end = group.indexOf("_");

            if (start > 0 && end > start) {
                String levelStr = group.substring(start, end);
                return AuthorizationTypeEnum.valueOf(levelStr);
            }
        } catch (Exception e) {
            // Ignora grupos com formato inesperado
        }
        return null;
    }
}
