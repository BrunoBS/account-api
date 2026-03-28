package com.brunobs.core.catalog.type.authorization;

import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing AuthorizationType catalog.
 * Handles specialized logic for settings and schema validation.
 */
@Service
public class AuthorizationTypeService extends BaseService<AuthorizationType, AuthorizationTypeDTO, Long> {

    public AuthorizationTypeService(AuthorizationTypeRepository repository,
                                    AuthorizationTypeMapper mapper,
                                    AuthorizationTypeValidator validator, CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);

    }

    /**
     * Unique identifier used for auditing and error context.
     */
    @Override
    public String getServiceIdentifier() {
        return "Authorization Type";
    }
}
