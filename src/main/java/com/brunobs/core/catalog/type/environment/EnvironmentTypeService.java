package com.brunobs.core.catalog.type.environment;

import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing EnvironmentType catalog lifecycle.
 * Extends BaseService to inherit generic CRUD, Validation, and Mapping logic.
 */
@Service
public class EnvironmentTypeService extends BaseService<EnvironmentType, EnvironmentTypeDTO, Long> {

    public EnvironmentTypeService(EnvironmentTypeRepository repository,
                                  EnvironmentTypeMapper mapper,
                                  EnvironmentTypeValidator validator,
                                  CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);
    }

    /**
     * Unique identifier for this service, used in audit logs and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Environment Type";
    }
}
