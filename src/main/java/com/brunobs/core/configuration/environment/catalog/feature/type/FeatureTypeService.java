package com.brunobs.core.catalog.feature.type;

import com.brunobs.core.catalog.feature.scope.FeatureScopeType;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeService;
import com.brunobs.shared.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service for managing Feature Types.
 * Orchestrates the relationship between a Feature and its specific Scope.
 */
@Service
public class FeatureTypeService extends BaseService<FeatureType, FeatureTypeDTO, Long> {

    private final FeatureScopeTypeService featureScopeService;

    public FeatureTypeService(FeatureTypeRepository repository,
                              FeatureTypeMapper mapper,
                              FeatureTypeValidator validator,
                              FeatureScopeTypeService featureScopeService,
                              MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
        this.featureScopeService = featureScopeService;
    }

    /**
     * Unique identifier used for auditing and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Feature Type";
    }

    @Override
    protected void applyAdditionalFields(FeatureType entity, FeatureTypeDTO dto) {
        FeatureScopeType scope = featureScopeService.findByName(dto.scope());
        entity.setFeatureScope(scope);
        entity.setAvailable(dto.available() != null && dto.available());
    }
}
