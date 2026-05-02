package com.brunobs.message.feature;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class SharingMessages extends MessageAbstract {

    private final String NAME_REQUIRED = "sharing.target.name.required";
    private final String NAME_INVALID = "sharing.target.name.invalid";
    private final String STATUS_INVALID = "sharing.target.status.invalid";
    private final String DESC_REQUIRED = "sharing.target.description.required";
    private final String DESC_INVALID = "sharing.target.description.invalid";
    private final String FEATURES_REQUIRED = "sharing.target.features.required";
    private final String FEATURES_INVALID = "sharing.target.features.invalid";
    private final String DUPLICATE_NAME = "sharing.target.duplicate.name";
    private final String DUPLICATE_FEATURES = "sharing.target.duplicate.features";
    private final String APP_SCOPE_INVALID = "sharing.target.application.scope.invalid";
    private static final String NOT_FOUND_ORIGIN = "sharing.origin.not.found";
    private static final String NOT_FOUND_TARGET = "sharing.target.not.found";
    private static final String NOT_FOUND_FEATURES = "sharing.features.not.found";
    private static final String SHARING_DUPLICATED = "sharing.origin.duplicated";
    private static final String SHARING_DESTINATION_SAME = "sharing.origin.destination.same";

    public SharingMessages(MessageSource messageSource) {
        super(messageSource);
    }

    public String nameRequired() {
        return getMessage(NAME_REQUIRED);
    }

    public String nameInvalid(int min, int max) {
        return getMessage(NAME_INVALID, min, max);
    }

    public String descriptionRequired() {
        return getMessage(DESC_REQUIRED);
    }

    public String descriptionInvalid(int min, int max) {
        return getMessage(DESC_INVALID, min, max);
    }

    public String featuresRequired() {
        return getMessage(FEATURES_REQUIRED);
    }

    public String featuresInvalid(String validFeatures) {
        return getMessage(FEATURES_INVALID, validFeatures);
    }

    public String duplicateName(String name) {
        return getMessage(DUPLICATE_NAME, name);
    }
    public String duplicateFeatures(String name) {
        return getMessage(DUPLICATE_FEATURES, name);
    }

    public String getNotFoundOrigin() {
        return getMessage(NOT_FOUND_ORIGIN);
    }

    public String sharingDuplicated() {
        return getMessage(SHARING_DUPLICATED);
    }

    public String sharingDestinstionSame() {
        return getMessage(SHARING_DESTINATION_SAME);
    }

    public String getNotFoundTarget() {
        return getMessage(NOT_FOUND_TARGET);
    }

    public String getNotFoundFeatures() {
        return getMessage(NOT_FOUND_FEATURES);
    }


    public String applicationScopeInvalid(String validScopes) {
        return getMessage(APP_SCOPE_INVALID, validScopes);
    }

    public String statusInvalid(String validStatus) {
        return getMessage(STATUS_INVALID, validStatus);
    }
}
