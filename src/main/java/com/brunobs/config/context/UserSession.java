package com.brunobs.config.context;

import java.util.Set;

public class UserSession {

    private final String userId;
    private final String traceId;
    private final Set<String> groups;

    public UserSession(String userId, String traceId, Set<String> groups) {
        this.userId = userId;
        this.traceId = traceId;
        this.groups = groups;
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
}