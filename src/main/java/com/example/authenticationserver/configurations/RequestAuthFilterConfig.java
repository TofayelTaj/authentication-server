package com.example.authenticationserver.configurations;

import com.example.authenticationserver.filters.RequestAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestAuthFilterConfig {

    @Bean
    public FilterRegistrationBean<RequestAuthFilter> filterFilterRegistrationBean(){
        FilterRegistrationBean<RequestAuthFilter>  filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new RequestAuthFilter());
        filterBean.addUrlPatterns("/app-user/**", "/admin/**");
        return filterBean;
    }
}
