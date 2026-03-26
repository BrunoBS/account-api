package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.schema.SchemaType;
import com.brunobs.core.catalog.type.schema.SchemaTypeDTO;
import com.brunobs.core.catalog.type.schema.SchemaTypeService;
import com.brunobs.shared.base.BaseController;
import com.brunobs.shared.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schema-type")
public class SchemaTypeController extends BaseController<SchemaTypeDTO, SchemaType, Long> {

    private final SchemaTypeService service;

    public SchemaTypeController(SchemaTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<SchemaType, SchemaTypeDTO, Long> getService() {
        return service;
    }

}
