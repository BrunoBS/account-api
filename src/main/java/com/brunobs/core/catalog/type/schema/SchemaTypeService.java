package com.brunobs.core.catalog.type.schema;

import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing the lifecycle of JSON Schemas.
 * Extends BaseService to inherit generic CRUD, Validation, and Mapping logic.
 */
@Service
public class SchemaTypeService extends BaseService<SchemaType, SchemaTypeDTO, Long> {

    public SchemaTypeService(SchemaTypeRepository repository,
                             SchemaTypeMapper mapper,
                             SchemaTypeValidator validator,
             CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);
    }

    /**
     * Unique identifier for this service, used in audit logs and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Schema Type";
    }
}
