package com.brunobs.auth.context;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSession {

    private long expirationTime;
    private String userName;
    private String email;

    private String accountId;
    private String applicationId;
    private String environmentId;
    private String traceId;

    private Set<String> groups;
    private Set<ParsedGroup> authorizerGroups;

    private static final String ADMIN_PORTAL_GROUP = "PM5_ORWER";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

    public UserSession() {
        // construtor vazio (necessário para frameworks / deserialização)
    }

    public UserSession(
            long expirationTime,
            String userName,
            String email,
            String accountId,
            String applicationId,
            String environmentId,
            String traceId,
            Set<String> groups) {

        this.expirationTime = expirationTime;
        this.userName = userName;
        this.email = email;
        this.accountId = accountId;
        this.applicationId = applicationId;
        this.environmentId = environmentId;
        this.traceId = traceId;

        this.groups = groups == null
                ? Set.of()
                : groups.stream()
                .filter(g -> g.startsWith("PM5"))
                .collect(Collectors.toSet());

        this.authorizerGroups = GroupParser.parse(groups);
    }

    // =========================
    // BUSINESS METHODS
    // =========================

    public boolean isOwner() {
        return groups != null && groups.contains(ADMIN_PORTAL_GROUP);
    }

    public boolean hasGroup(String group) {
        return groups != null && groups.contains(group);
    }

    public boolean hasAuthorizer(String suffix) {
        return authorizerGroups.contains(suffix);
    }

    public String getExpirationFormatted() {
        return String.format(
                "Atual: %s | Expira em: %s",
                FORMATTER.format(Instant.now()),
                FORMATTER.format(Instant.ofEpochMilli(expirationTime))
        );
    }

    // =========================
    // GETTERS
    // =========================

    public long getExpirationTime() {
        return expirationTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
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

    public String getTraceId() {
        return traceId;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public Set<ParsedGroup> getAuthorizerGroups() {
        return authorizerGroups;
    }

    // =========================
    // SETTERS
    // =========================

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups == null
                ? Set.of()
                : groups.stream()
                .filter(g -> g.startsWith("PM5"))
                .collect(Collectors.toSet());

        this.authorizerGroups = GroupParser.parse(groups);
    }

    public void setAuthorizerGroups(Set<ParsedGroup> authorizerGroups) {
        this.authorizerGroups = authorizerGroups;
    }
}