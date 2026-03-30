package com.brunobs.message.feature;

import com.brunobs.message.MessageAbstract;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class CatalogMessages extends MessageAbstract {

    private final String CATALOG_REQUIRED = "catalog.required";
    private final String DESCRIPTION_REQUIRED = "catalog.description.required";
    private final String DESCRIPTION_INVALID_LENGTH = "catalog.description.invalid.length";
    private final String NAME_DUPLICATE = "catalog.name.duplicate";
    private final String NAME_INVALID = "catalog.name.invalid";
    private final String SCOPE_INVALID = "catalog.scope.invalid";
    private final String LABEL_INVALID = "catalog.label.required";
    private static final String NOT_FOUND = "catalog.not.found";
    private static final String RESTORE_INVALID = "catalog.restore.invalid";

    public String notFound(String feature) {
        return getMessage(NOT_FOUND,feature);
    }

    public String restoreInvalid(String feature) {
        return getMessage(RESTORE_INVALID, feature);
    }

    public CatalogMessages(MessageSource messageSource) {
        super(messageSource);
    }

    public String descriptionRequired(String feature) {
        return getMessage(DESCRIPTION_REQUIRED, feature);
    }

    public String labelRequired(String feature) {
        return getMessage(LABEL_INVALID, feature);
    }

    public String descriptionInvalidLength(String feature, int min, int max) {
        return getMessage(DESCRIPTION_INVALID_LENGTH, feature, min, max);
    }

    public String nameDuplicate(String feature, String name) {
        return getMessage(NAME_DUPLICATE, feature, name);
    }

    public String nameInvalid(String feature, String catalogNamesValid) {
        return getMessage(NAME_INVALID, feature, catalogNamesValid);
    }
    public String scopeInvalid(String feature, String catalogNamesValid) {
        return getMessage(SCOPE_INVALID, feature, catalogNamesValid);
    }


    public String recordRequired() {
        return getMessage(CATALOG_REQUIRED);
    }
}
