package com.brunobs.message.general;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class GlobalMessages extends MessageAbstract {

    private final String VALIDATION_FAILED = "global.validation.failed";
    private final String REQUEST_READABLE = "global.request.readable";
    private final String REQUEST_FORMAT = "global.request.format";
    private final String DATA_INTEGRITY = "global.data.integrity";
    private final String NOT_FOUND = "global.resource.not.found";
    private final String INTERNAL_ERROR = "global.internal.server.error";
    private final String USER_ACCESS_DENAID = "global.user.access.denied";
    private static final String USER_SESSION_NOT_FOUND = "global.auth.user.session.not.found";
    private static final String USER_GROUPS_NOT_FOUND = "global.auth.user.groups.not.found";
    private static final String USER_GROUP_MISSING = "global.auth.user.group.missing";
    private static final String USER_NOT_OWNER = "global.auth.user.not.owner";
    private static final String TYPE_MISMATCH = "global.validation.type.mismatch";


    public GlobalMessages(MessageSource messageSource) {
        super(messageSource);
    }

    public String validationFailed() {
        return getMessage(VALIDATION_FAILED);
    }

    public String requestReadable() {
        return getMessage(REQUEST_READABLE);
    }

    public String requestFormat() {
        return getMessage(REQUEST_FORMAT);
    }

    public String dataIntegrity() {
        return getMessage(DATA_INTEGRITY);
    }

    public String resourceNotFound() {
        return getMessage(NOT_FOUND);
    }

    public String userAccessDenaid() {
        return getMessage(USER_ACCESS_DENAID);
    }

    public String userSessionNotFound() {
        return getMessage(USER_SESSION_NOT_FOUND);
    }

    public String userGroupsNotFound() {
        return getMessage(USER_GROUPS_NOT_FOUND);
    }

    public String userGroupMissing() {
        return getMessage(USER_GROUP_MISSING);
    }

    public String internalServerError(Object errorId) {
        return getMessage(INTERNAL_ERROR, errorId);
    }

    public String typeMismatch(String field, String type) {
        return getMessage(TYPE_MISMATCH, field, type);
    }

    public String userNotOwner() {
        return getMessage(USER_NOT_OWNER);
    }
}
