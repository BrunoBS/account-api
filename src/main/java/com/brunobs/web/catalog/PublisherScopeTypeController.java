package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.publisherscope.PublisherScopeType;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeService;
import com.brunobs.shared.BaseController;
import com.brunobs.shared.BaseService;
import com.brunobs.core.catalog.type.publisherscope.PublisherScopeTypeDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publisher-scope-type")
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
