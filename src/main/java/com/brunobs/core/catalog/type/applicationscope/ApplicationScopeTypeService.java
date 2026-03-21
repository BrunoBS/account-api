package com.brunobs.core.catalog.type.applicationscope;


import com.brunobs.shared.BaseService;
import org.springframework.context.MessageSource;
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
                                       MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
    }

    @Override
    public String getServiceIdentifier() {
        return "Application Scope Type";
    }
}
