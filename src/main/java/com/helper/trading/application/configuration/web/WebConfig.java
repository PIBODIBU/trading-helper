package com.helper.trading.application.configuration.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/*@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport*/
public class WebConfig extends WebMvcConfigurationSupport {
    /*@Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/jsp/");
        return resolver;
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/resources/**")) {
            registry.addResourceHandler("/resources/**").addResourceLocations(
                    "/static/");
        }
    }
}
