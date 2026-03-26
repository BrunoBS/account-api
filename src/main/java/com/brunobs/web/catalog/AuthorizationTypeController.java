package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.authorization.AuthorizationType;
import com.brunobs.core.catalog.type.authorization.AuthorizationTypeDTO;
import com.brunobs.core.catalog.type.authorization.AuthorizationTypeService;
import com.brunobs.shared.base.BaseController;
import com.brunobs.shared.base.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authorization-type")
public class AuthorizationTypeController extends BaseController<AuthorizationTypeDTO, AuthorizationType, Long> {

    private final AuthorizationTypeService service;

    public AuthorizationTypeController(AuthorizationTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<AuthorizationType, AuthorizationTypeDTO, Long> getService() {
        return service;
    }
}
