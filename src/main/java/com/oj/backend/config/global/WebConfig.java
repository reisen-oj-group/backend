package com.oj.backend.config.global;

import com.oj.backend.utils.jwt.JwtInterceptor;
import com.oj.backend.utils.web.StringToIntegerConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;

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

    /**
     * 注册JWT拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/auth/login",
                        "/api/auth/register",
                        "/api/sync-config",
                        "/api/user",
                        "/api/problem/list"     // 该页面前端已显式传入userid
                );
    }
}
