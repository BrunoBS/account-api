package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeType;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeDTO;
import com.brunobs.core.catalog.type.applicationscope.ApplicationScopeTypeService;
import com.brunobs.shared.base.BaseController;
import com.brunobs.shared.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application-scope-type")
public class ApplicationScopeTypeController extends BaseController<ApplicationScopeTypeDTO, ApplicationScopeType, Long> {

    private final ApplicationScopeTypeService service;

    public ApplicationScopeTypeController(ApplicationScopeTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<ApplicationScopeType, ApplicationScopeTypeDTO, Long> getService() {
        return service;
    }
}
