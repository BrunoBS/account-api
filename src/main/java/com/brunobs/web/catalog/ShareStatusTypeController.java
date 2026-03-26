package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.sharestatus.ShareStatusType;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeDTO;
import com.brunobs.core.catalog.type.sharestatus.ShareStatusTypeService;
import com.brunobs.shared.base.BaseController;
import com.brunobs.shared.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/share-status-type")
public class ShareStatusTypeController extends BaseController<ShareStatusTypeDTO, ShareStatusType, Long> {

    private final ShareStatusTypeService service;

    public ShareStatusTypeController(ShareStatusTypeService service) {
             this.service = service;
    }

    @Override
    protected BaseService<ShareStatusType, ShareStatusTypeDTO, Long> getService() {
        return service;
    }
}
