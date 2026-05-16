package com.brunobs.core.catalog.type.applicationscope;


import com.brunobs.core.catalog.type.authorization.ApplicationScopeTypeMapper;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;

import org.springframework.stereotype.Service;

/**
 * Service for managing ApplicationScopeType catalog.
 * Orchestrates Repository, Mapper, and Validator with built-in i18n support.
 */
@Service
public class ApplicationScopeTypeService extends BaseService<ApplicationScopeType, ApplicationScopeTypeDTO, Long> {

    public ApplicationScopeTypeService(ApplicationScopeTypeRepository repository,
                                       ApplicationScopeTypeMapper mapper,
                                       ApplicationScopeTypeValidator validator,
                                       CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);
    }
}
