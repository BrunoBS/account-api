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
    private String tokenJwt;

    private Set<String> groups;
    private Set<ParsedGroup> authorizerGroups;

    private static final String ADMIN_PORTAL_GROUP = "PM5_ORWER";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

    public UserSession() {
        // construtor vazio (necessário para frameworks / deserialização)
    }


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
        this.groups = groups == null ? Set.of() : groups;
    }

    public void setAuthorizerGroups(Set<ParsedGroup> authorizerGroups) {
        this.authorizerGroups = authorizerGroups;
    }

    public String getTokenJwt() {
        return tokenJwt;
    }

    public void setTokenJwt(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }
}