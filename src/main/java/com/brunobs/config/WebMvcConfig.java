package com.brunobs.config;


import com.brunobs.auth.authorization.AuthorizationPolicyRegistry;
import com.brunobs.auth.authorization.AuthorizationService;
import com.brunobs.auth.security.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthorizationService authorizationService;
    private final AuthorizationPolicyRegistry authorizationPolicyRegistry;

    public WebMvcConfig(AuthorizationService authorizationService,
                        AuthorizationPolicyRegistry authorizationPolicyRegistry) {
        this.authorizationService = authorizationService;
        this.authorizationPolicyRegistry = authorizationPolicyRegistry;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AuthorizationInterceptor(authorizationService, authorizationPolicyRegistry))
                .addPathPatterns("/**");
    }
}