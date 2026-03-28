package com.brunobs.message.general;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class AuditMessages extends MessageAbstract {

    private final String ACTION_SUCCESS = "audit.action.success";

    public AuditMessages(MessageSource messageSource) {
        super(messageSource);
    }
    public String actionSuccess(String action, String user) {
        return getMessage(ACTION_SUCCESS, action, user);
    }
}
