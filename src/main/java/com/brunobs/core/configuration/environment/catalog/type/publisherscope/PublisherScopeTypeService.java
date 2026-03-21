package com.brunobs.core.catalog.type.publisherscope;

import com.brunobs.shared.BaseService;
import org.springframework.context.MessageSource;
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
                                     MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
    }

    /**
     * Unique identifier used for auditing and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Publisher Scope Type";
    }
}
