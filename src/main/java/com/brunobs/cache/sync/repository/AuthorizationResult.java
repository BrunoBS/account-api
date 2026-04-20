package com.brunobs.cache.sync.repository;

public interface AuthorizationResult {
    Long getIdAccount();

    String getIdEnvironment();

    Long getIdApplication();

    String getAuthorizerGroup();
}