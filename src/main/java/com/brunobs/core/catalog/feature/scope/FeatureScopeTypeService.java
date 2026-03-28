package com.brunobs.core.catalog.feature.scope;

import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service
public class FeatureScopeTypeService extends BaseService<FeatureScopeType, FeatureScopeTypeDTO, Long> {

    public FeatureScopeTypeService(FeatureScopeTypeRepository repository,
                                   FeatureScopeTypeMapper mapper,
                                   FeatureScopeTypeValidator validator,
                                   CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);
    }


    @Override
    public String getServiceIdentifier() {
        return "Feature Scope Type";
    }
}
