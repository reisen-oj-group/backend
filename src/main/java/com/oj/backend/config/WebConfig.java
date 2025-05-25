package com.oj.backend.config;

import com.oj.backend.utils.converter.StringToIntegerConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new StringToIntegerConverter());
    }
}
