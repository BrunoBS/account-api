package com.brunobs.auth.authorization;

import com.brunobs.auth.cache.AuthorizationCacheService;
import com.brunobs.auth.context.ParsedGroup;
import com.brunobs.auth.context.UserSession;
import com.brunobs.core.catalog.type.authorization.AuthorizationTypeEnum;
import com.brunobs.exception.AccessDeniedException;
import com.brunobs.message.general.GlobalMessages;
import com.brunobs.shared.validation.ValidationResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

@Service
public class AuthorizationService {

    private final GlobalMessages globalMessages;
    private final AuthorizationCacheService authCache;

    public AuthorizationService(GlobalMessages globalMessages,
                                AuthorizationCacheService authCache) {
        this.globalMessages = globalMessages;
        this.authCache = authCache;
    }

    public void checkAuthorization(UserSession session, AuthorizationLevel level) {

        if (session == null) {
            throw new AccessDeniedException(
                    new ValidationResult("authentication", globalMessages.userSessionNotFound()));
        }

        // bypass rules
        if (session.isOwner() || AuthorizationLevel.OPEN.equals(level)) {
            return;
        }

        String cacheKey = buildCacheKey(session, level);

        String group = authCache.getAuthorizationGroup(
                Long.valueOf(session.getAccountId()),
                level.name(),
                cacheKey
        );

        // cache miss ou ainda não carregado
        if (!StringUtils.hasText(group)) {
            throw new AccessDeniedException(
                    new ValidationResult("authentication", globalMessages.userGroupsNotFound()));
        }

        // valida acesso baseado nos grupos carregados
        if (!hasAccess(session.getAuthorizerGroups(), group)) {
            throw new AccessDeniedException(
                    new ValidationResult("authentication", globalMessages.userGroupMissing()));
        }
    }

    private String buildCacheKey(UserSession session, AuthorizationLevel level) {
        return String.format(
                "AUTH::%s::%s::%s",
                session.getAccountId(),
                session.getEnvironmentId() != null
                        ? session.getEnvironmentId()
                        : level.name(),
                session.getApplicationId() != null
                        ? session.getApplicationId()
                        : "NONE"
        );
    }

    public boolean hasAccess(Set<ParsedGroup> userGroups, String requiredGroup) {

        AuthorizationTypeEnum required = AuthorizationTypeEnum.fromGroupName(requiredGroup);
        String suffixRequired = extractSuffix(requiredGroup);

        return userGroups.stream().anyMatch(group -> {

            String suffixUser = extractSuffix(group.fullGroup());

            if (!suffixRequired.equals(suffixUser)) {
                return false;
            }

            AuthorizationTypeEnum userLevel =
                    AuthorizationTypeEnum.fromGroupName(group.fullGroup());

            return userLevel.getOrder() >= required.getOrder();
        });
    }

    private String extractSuffix(String groupName) {

        if (groupName == null || !groupName.contains("_")) {
            return "";
        }

        return groupName.substring(groupName.indexOf("_"));
    }
}