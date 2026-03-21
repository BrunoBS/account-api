package com.brunobs.core.catalog.feature.scope;
import com.brunobs.shared.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing FeatureScopeType catalog lifecycle.
 * Inherits generic CRUD, Validation, and Mapping logic from BaseService.
 */
@Service
public class FeatureScopeTypeService extends BaseService<FeatureScopeType, FeatureScopeTypeDTO, Long> {

    public FeatureScopeTypeService(FeatureScopeTypeRepository repository,
                                   FeatureScopeTypeMapper mapper,
                                   FeatureScopeTypeValidator validator,
                                   MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
    }

    /**
     * Unique identifier used for auditing and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Feature Scope Type";
    }
}
