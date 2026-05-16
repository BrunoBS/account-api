package com.brunobs.core.catalog.type.publisherscope;

import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing PublisherScopeType catalog lifecycle.
 * Inherits generic CRUD, Validation, and Mapping logic from BaseService.
 */
@Service
public class PublisherScopeTypeService extends BaseService<PublisherScopeType, PublisherScopeTypeDTO, Long> {

    public PublisherScopeTypeService(PublisherScopeTypeRepository repository,
                                     PublisherScopeTypeMapper mapper,
                                     PublisherScopeTypeValidator validator,
                                     CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);
    }

}
