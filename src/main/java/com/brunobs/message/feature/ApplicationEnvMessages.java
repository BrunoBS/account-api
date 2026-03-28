package com.brunobs.message.feature;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ApplicationEnvMessages extends MessageAbstract {

    private final String DUPLICATE_PUBLISHER = "application.env.duplicate.publisher";
    private final String INCONSISTENT_APLICATION = "application.env.inconsistent.application";
    private final String MIN_PUBLISHER = "application.env.min.publisher";
    private final String ORDER_REQUIRED = "application.env.order.required";
    private final String APLICATION_ENV_REQUIRED = "application.env.required";
    private final String NOT_FOUND = "application.env.not.found";
    private final String SELECTION_REQUIRED = "application.env.selection.required";

    public ApplicationEnvMessages(MessageSource messageSource) {
        super(messageSource);
    }

    public String duplicatePublisher() {
        return getMessage(DUPLICATE_PUBLISHER);
    }

    public String inconsistentapplication(Object applicationName) {
        return getMessage(INCONSISTENT_APLICATION, applicationName);
    }

    public String minPublisher(int min) {
        return getMessage(MIN_PUBLISHER, min);
    }

    public String orderRequired() {
        return getMessage(ORDER_REQUIRED);
    }


    public String recordRequired() {
        return getMessage(APLICATION_ENV_REQUIRED);
    }

    public String notFound() {
        return getMessage(NOT_FOUND);
    }

    public String selectionRequired() {
        return getMessage(SELECTION_REQUIRED);
    }

}
