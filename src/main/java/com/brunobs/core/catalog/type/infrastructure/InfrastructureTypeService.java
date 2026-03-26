package com.brunobs.core.catalog.type.infrastructure;
import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing InfrastructureType catalog lifecycle.
 * Inherits generic CRUD, Validation, and Mapping logic from BaseService.
 */
@Service
public class InfrastructureTypeService extends BaseService<InfrastructureType, InfrastructureTypeDTO, Long> {

    public InfrastructureTypeService(InfrastructureTypeRepository repository,
                                     InfrastructureTypeMapper mapper,
                                     InfrastructureTypeValidator validator,
                                     MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
    }

    /**
     * Unique identifier used for auditing and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Infrastructure Type";
    }
}
