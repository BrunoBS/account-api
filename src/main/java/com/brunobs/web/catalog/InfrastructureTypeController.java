package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.infrastructure.InfrastructureType;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureTypeService;
import com.brunobs.shared.BaseController;
import com.brunobs.shared.BaseService;
import com.brunobs.core.catalog.type.infrastructure.InfrastructureTypeDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/infrastructure-type")
public class InfrastructureTypeController extends BaseController<InfrastructureTypeDTO, InfrastructureType, Long> {

    private final InfrastructureTypeService service;

    public InfrastructureTypeController(InfrastructureTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<InfrastructureType, InfrastructureTypeDTO, Long> getService() {
        return service;
    }
}
