package com.oj.backend.config.global;

import com.oj.backend.utils.web.StringToIntegerConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Web MVC 全局配置类.
 *
 * <p><strong>主要功能：</strong>
 * <ul>
 *   <li>注册自定义类型转换器</li>
 *   <li>扩展 Spring MVC 默认配置</li>
 * </ul>
 *
 * <p><strong>当前配置：</strong>
 * 添加了 {@code String -> Integer} 的类型转换器
 *
 * @see WebMvcConfigurer Spring MVC 配置接口
 * @see FormatterRegistry 类型转换器注册器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加自定义类型转换器.
     *
     * <p><strong>注意：</strong>
     * <ul>
     *   <li>已注释默认父类实现 {@code WebMvcConfigurer.super.addFormatters()}</li>
     *   <li>当前仅注册 {@link StringToIntegerConverter} 转换器</li>
     * </ul>
     *
     * @param registry Spring 类型转换器注册中心（非空）
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        //        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new StringToIntegerConverter());
    }
}
