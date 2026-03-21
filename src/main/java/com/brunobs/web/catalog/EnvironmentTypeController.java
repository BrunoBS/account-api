package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.environment.EnvironmentType;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeDTO;
import com.brunobs.core.catalog.type.environment.EnvironmentTypeService;
import com.brunobs.shared.BaseController;
import com.brunobs.shared.BaseService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/environment-type")
public class EnvironmentTypeController extends BaseController<EnvironmentTypeDTO, EnvironmentType, Long> {

    private final EnvironmentTypeService service;

    public EnvironmentTypeController(EnvironmentTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<EnvironmentType, EnvironmentTypeDTO, Long> getService() {
        return service;
    }
}
