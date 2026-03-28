package com.brunobs.core.catalog.feature.type;

import com.brunobs.core.catalog.feature.scope.FeatureScopeType;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeService;
import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service
public class FeatureTypeService extends BaseService<FeatureType, FeatureTypeDTO, Long> {

    private final FeatureScopeTypeService featureScopeService;

    public FeatureTypeService(FeatureTypeRepository repository,
                              FeatureTypeMapper mapper,
                              FeatureTypeValidator validator,
                              FeatureScopeTypeService featureScopeService,
                              CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);
        this.featureScopeService = featureScopeService;
    }


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
