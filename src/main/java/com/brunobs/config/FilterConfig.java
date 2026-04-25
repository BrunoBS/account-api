package com.brunobs.config;

import com.brunobs.auth.security.filter.AuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterRegistration(
            AuthorizationFilter authorizationFilter
    ) {

        FilterRegistrationBean<AuthorizationFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(authorizationFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registration;
    }
}