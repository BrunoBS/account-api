package com.brunobs.core.catalog.type.sharestatus;
import com.brunobs.shared.base.BaseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing ShareStatusType catalog lifecycle.
 * Inherits generic CRUD, Validation, and Mapping logic from BaseService.
 */
@Service
public class ShareStatusTypeService extends BaseService<ShareStatusType, ShareStatusTypeDTO, Long> {

    public ShareStatusTypeService(ShareStatusTypeRepository repository,
                                  ShareStatusTypeMapper mapper,
                                  ShareStatusTypeValidator validator,
                                  MessageSource messageSource) {
        super(repository, mapper, validator, messageSource);
    }

    /**
     * Unique identifier used for auditing and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Share Status Type";
    }
}
