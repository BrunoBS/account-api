package com.brunobs.cache.repository;

public interface AuthorizationResult {
    Long getIdAccount();

    String getIdEnvironment();

    Long getIdApplication();

    String getAuthorizerGroup();
}