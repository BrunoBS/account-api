package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.publisherscope.PublisherScopeType;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeDTO;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeService;
import com.brunobs.shared.base.BaseController;
import com.brunobs.shared.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publisher-scope-type")
public class PublisherScopeTypeController extends BaseController<PublisherScopeTypeDTO, PublisherScopeType, Long> {

    private final PublisherScopeTypeService service;

    public PublisherScopeTypeController(PublisherScopeTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<PublisherScopeType, PublisherScopeTypeDTO, Long> getService() {
        return service;
    }
}
