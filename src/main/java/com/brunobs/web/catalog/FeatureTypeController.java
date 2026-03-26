package com.brunobs.web.catalog;

import com.brunobs.core.catalog.feature.type.FeatureType;
import com.brunobs.core.catalog.feature.type.FeatureTypeDTO;
import com.brunobs.core.catalog.feature.type.FeatureTypeService;
import com.brunobs.shared.base.BaseController;
import com.brunobs.shared.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feature-type")
public class FeatureTypeController extends BaseController<FeatureTypeDTO, FeatureType, Long> {

    private final FeatureTypeService service;

    public FeatureTypeController(FeatureTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<FeatureType, FeatureTypeDTO, Long> getService() {
        return service;
    }
}
