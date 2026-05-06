package com.brunobs.web.admin;

import com.brunobs.auth.authorization.AuthorizationLevel;
import com.brunobs.auth.authorization.AuthorizationRequired;
import com.brunobs.auth.messaging.dto.AccountCacheDTO;
import com.brunobs.cache.AuthorizationCacheRefreshService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/auth/cache")
@AuthorizationRequired(level = AuthorizationLevel.OWNER)
public class AuthorizationCacheAdminController {

    private final AuthorizationCacheRefreshService service;

    public AuthorizationCacheAdminController(AuthorizationCacheRefreshService service) {
        this.service = service;
    }

    @PostMapping("/refresh")
    public void refreshAll() {
        service.refresh(new AccountCacheDTO(null, "DEV", null));
    }

    @PostMapping("/refresh/{accountId}")
    public void refreshAccount(@PathVariable Long accountId) {
        service.refresh(new AccountCacheDTO(accountId, "DEV", null));
    }
}