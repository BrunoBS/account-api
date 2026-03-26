package com.brunobs.core.catalog.type.language;
import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
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
                               MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
    }

    /**
     * Unique identifier used for auditing and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Language Type";
    }
}
