package com.brunobs.config.context;

import java.util.Set;
import java.util.stream.Collectors;

public class UserSession {

    private final String userId;
    private final String accountId;
    private final String applicationId;
    private final String environmentId;
    private final String traceId;
    private final Set<String> groups;
    private Set<String> authorizerGroups;
    private static final String ADMIN_PORTAL_GROUP = "PM5_ORWER";

    public UserSession(
            String userId,
            String accountId,
            String applicationId,
            String environmentId,
            String traceId, Set<String> groups) {
        this.userId = userId;
        this.accountId = accountId;
        this.applicationId = applicationId;
        this.environmentId = environmentId;
        this.traceId = traceId;
        this.groups = groups == null ? Set.of() : groups.stream().filter(f -> f.startsWith("PM5")).collect(Collectors.toSet());
        this.authorizerGroups = GroupParser.parse(groups)
                .stream()
                .map(ParsedGroup::authorizer)
                .collect(Collectors.toSet());
    }

    public boolean hasAuthorizer(String suffix) {
        return authorizerGroups.contains(suffix);
    }

    public boolean isOwner() {
        return this.getGroups().contains(ADMIN_PORTAL_GROUP);
    }

    public String getUserId() {
        return userId;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public boolean hasGroup(String group) {
        return groups.contains(group);
    }

    public String getTraceId() {
        return traceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getEnvironmentId() {
        return environmentId;
    }
}