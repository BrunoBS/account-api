package com.brunobs.core.catalog.type.sharestatus;
import com.brunobs.message.feature.CatalogMessages;
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
                                  CatalogMessages catalogMessages) {
        super(repository, mapper, validator, catalogMessages);
    }

    /**
     * Unique identifier used for auditing and error contexts.
     */
    @Override
    public String getServiceIdentifier() {
        return "Share Status Type";
    }
}
