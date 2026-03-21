package com.brunobs.web.catalog;

import com.brunobs.core.catalog.type.language.LanguageType;
import com.brunobs.core.catalog.type.language.LanguageTypeService;
import com.brunobs.shared.BaseController;
import com.brunobs.shared.BaseService;
import com.brunobs.core.catalog.type.language.LanguageTypeDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/language-type")
public class LanguageTypeController extends BaseController<LanguageTypeDTO, LanguageType, Long> {

    private final LanguageTypeService service;

    public LanguageTypeController(LanguageTypeService service) {
        this.service = service;
    }

    @Override
    protected BaseService<LanguageType, LanguageTypeDTO, Long> getService() {
        return service;
    }
}
