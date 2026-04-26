package com.brunobs.auth.authorization;

public class AuthorizationPolicy {

    private final AuthorizationLevel level;
    private final Source source;

    public AuthorizationPolicy(AuthorizationLevel level, Source source) {
        this.level = level;
        this.source = source;
    }

    public AuthorizationLevel getLevel() {
        return level;
    }

    public Source getSource() {
        return source;
    }

    public static AuthorizationPolicy open() {
        return new AuthorizationPolicy(
                AuthorizationLevel.OPEN,
                Source.DEFAULT
        );
    }

    @Override
    public String toString() {
        return "AuthorizationPolicy{" +
                "level=" + level +
                ", source=" + source +
                '}';
    }


    public enum Source {
        METHOD,
        CLASS,
        DEFAULT
    }
}