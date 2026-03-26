package com.brunobs.filter;

import com.brunobs.config.context.UserSession;

public class AuthorizationContext {

    private final String authorizationTypeName;
    private final String authorizerGroup;
    private final UserSession userContext;

    private AuthorizationContext(Builder builder) {
        this.authorizationTypeName = builder.authorizationTypeName;
        this.authorizerGroup = builder.authorizerGroup;
        this.userContext = builder.userContext;
    }

    public String getAuthorizationTypeName() {
        return authorizationTypeName;
    }

    public String getAuthorizerGroup() {
        return authorizerGroup;
    }

    public UserSession getUserContext() {
        return userContext;
    }

    public boolean isOwner() {
        return userContext.getGroups().contains("OWNER");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String authorizationTypeName;
        private String authorizerGroup;
        private UserSession userContext;

        public Builder authorizationType(String authorizationTypeName) {
            this.authorizationTypeName = authorizationTypeName;
            return this;
        }

        public Builder authorizerGroup(String authorizerGroup) {
            this.authorizerGroup = authorizerGroup;
            return this;
        }

        public Builder userContext(UserSession userContext) {
            this.userContext = userContext;
            return this;
        }

        public AuthorizationContext build() {
            return new AuthorizationContext(this);
        }
    }
}