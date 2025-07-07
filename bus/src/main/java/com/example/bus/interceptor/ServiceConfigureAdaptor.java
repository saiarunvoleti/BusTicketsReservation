package com.example.bus.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServiceConfigureAdaptor implements WebMvcConfigurer {

    @Autowired
    private ServiceAuthInterceptor travellerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(travellerInterceptor)
            .addPathPatterns("/traveller/**");  // only for these paths
    }
}
