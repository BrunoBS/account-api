package com.brunobs.config.security;

import jakarta.servlet.http.HttpServletRequest;

public class PathVariableUtils {

    public static class PathIds {
        private final String accountId;
        private final String environmentId;
        private final String applicationId;

        public PathIds(String accountId, String environmentId, String applicationId) {
            this.accountId = accountId;
            this.environmentId = environmentId;
            this.applicationId = applicationId;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getEnvironmentId() {
            return environmentId;
        }

        public String getApplicationId() {
            return applicationId;
        }

        @Override
        public String toString() {
            return "PathIds{" +
                    "accountId=" + accountId +
                    ", environmentId=" + environmentId +
                    ", applicationId=" + applicationId +
                    '}';
        }
    }

    public static PathIds extractIds(HttpServletRequest request) {
        String path = request.getRequestURI();
        String[] segments = path.split("/");

        String accountId = null;
        String environmentId = null;
        String applicationId = null;

        for (int i = 0; i < segments.length - 1; i++) {
            String key = segments[i];
            String value = segments[i + 1];

            try {
                switch (key) {
                    case "accounts":
                        accountId = value;
                        break;
                    case "environments":
                        environmentId = value;
                        break;
                    case "applications":
                        applicationId = value;
                        break;
                }
            } catch (NumberFormatException e) {
            }
        }

        return new PathIds(accountId, environmentId, applicationId);
    }
}