package com.brunobs.core.catalog.type.authorization;

import com.brunobs.shared.BaseService;
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
                                    AuthorizationTypeValidator validator,
                                    MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
    }

    /**
     * Unique identifier used for auditing and error context.
     */
    @Override
    public String getServiceIdentifier() {
        return "Authorization Type";
    }
}
