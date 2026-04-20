package com.brunobs.auth.context;

public class UserContext {

    private static final ThreadLocal<UserSession> CONTEXT = new ThreadLocal<>();

    public static void set(UserSession session) {
        CONTEXT.set(session);
    }

    public static UserSession get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}