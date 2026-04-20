package com.brunobs.auth.authorization;

import java.util.Objects;

public final class AuthorizationPolicy {

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

    /**
     * Origem da regra de autorização
     */
    public enum Source {
        METHOD,
        CLASS,
        DEFAULT
    }

    /**
     * Cria uma policy padrão OPEN
     */
    public static AuthorizationPolicy open() {
        return new AuthorizationPolicy(AuthorizationLevel.OPEN, Source.DEFAULT);
    }

    /**
     * Cria policy baseada em método
     */
    public static AuthorizationPolicy fromMethod(AuthorizationLevel level) {
        return new AuthorizationPolicy(level, Source.METHOD);
    }

    /**
     * Cria policy baseada em classe
     */
    public static AuthorizationPolicy fromClass(AuthorizationLevel level) {
        return new AuthorizationPolicy(level, Source.CLASS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorizationPolicy)) return false;
        AuthorizationPolicy that = (AuthorizationPolicy) o;
        return level == that.level && source == that.source;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, source);
    }

    @Override
    public String toString() {
        return "AuthorizationPolicy{" +
                "level=" + level +
                ", source=" + source +
                '}';
    }
}