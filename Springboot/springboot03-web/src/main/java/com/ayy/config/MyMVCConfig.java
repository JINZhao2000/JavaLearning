package com.ayy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/03/2021
 * @ Version 1.0
 */
@Configuration
public class MyMVCConfig implements WebMvcConfigurer {
    // ViewResolver
    @Bean
    public ViewResolver myViewResolver(){
        return new MyViewResolver();
    }

    public static class MyViewResolver implements ViewResolver{
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello2").setViewName("hello");
    }
}
