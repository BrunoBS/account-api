package com.brunobs.config;


import com.brunobs.auth.authorization.AuthorizationPolicyRegistry;
import com.brunobs.auth.authorization.AuthorizationService;
import com.brunobs.auth.security.AuthorizationClientService;
import com.brunobs.auth.security.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthorizationClientService authorizationClientService;

    public WebMvcConfig(AuthorizationClientService authorizationClientService) {
        this.authorizationClientService = authorizationClientService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AuthorizationInterceptor(authorizationClientService))
                .addPathPatterns("/**");
    }
}