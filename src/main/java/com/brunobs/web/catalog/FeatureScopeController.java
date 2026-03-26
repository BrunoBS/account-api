package com.brunobs.web.catalog;


import com.brunobs.core.catalog.feature.scope.FeatureScopeType;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeDTO;
import com.brunobs.core.catalog.feature.scope.FeatureScopeTypeService;
import com.brunobs.shared.base.BaseController;
import com.brunobs.shared.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/feature-scope")
public class FeatureScopeController extends BaseController<FeatureScopeTypeDTO, FeatureScopeType, Long> {

    private final FeatureScopeTypeService service;

    public FeatureScopeController(FeatureScopeTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<FeatureScopeType, FeatureScopeTypeDTO, Long> getService() {
        return service;
    }
}
