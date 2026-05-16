package com.brunobs.core.catalog.type.language;

import com.brunobs.message.feature.CatalogMessages;
import com.brunobs.shared.base.BaseService;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing Programming Language Type catalog lifecycle.
 * Inherits generic CRUD, Validation, and Mapping logic from BaseService.
 */
@Service
public class LanguageTypeService extends BaseService<LanguageType, LanguageTypeDTO, Long> {

    public LanguageTypeService(LanguageTypeRepository repository,
                               LanguageTypeMapper mapper,
                               LanguageTypeValidator validator,
                               CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);
    }
}
