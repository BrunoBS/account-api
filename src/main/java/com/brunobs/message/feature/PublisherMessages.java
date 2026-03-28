package com.brunobs.message.feature;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class PublisherMessages extends MessageAbstract {

    private static final String PUBLISHER_REQUIRED = "publisher.required";
    private final String NAME_INVALID = "publisher.name.invalid";
    private final String LABEL_REQUIRED = "publisher.label.required";
    private final String DESC_REQUIRED = "publisher.description.required";
    private final String SCOPE_INVALID = "publisher.scope.invalid";
    private final String NAME_DUPLICATE = "publisher.name.duplicate";
    private final String NOT_FOUND = "publisher.not.found";

    public PublisherMessages(MessageSource messageSource) {
        super(messageSource);
    }

    public String nameInvalid(String validNames) {
        return getMessage(NAME_INVALID, validNames);
    }

    public String labelRequired() {
        return getMessage(LABEL_REQUIRED);
    }

    public String descriptionRequired() {
        return getMessage(DESC_REQUIRED);
    }

    public String notFound() {
        return getMessage(NOT_FOUND);
    }


    public String scopeInvalid(String validScopes) {
        return getMessage(SCOPE_INVALID, validScopes);
    }


    public String recordRequired() {
        return getMessage(PUBLISHER_REQUIRED);
    }

    public String nameDuplicate(String name) {
        return getMessage(NAME_DUPLICATE, name);
    }
}
