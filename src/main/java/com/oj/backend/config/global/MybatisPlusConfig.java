package com.oj.backend.config.global;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 配置类.
 *
 * <p>主要作用：
 * <ul>
 *   <li>配置分页拦截器（{@link PaginationInnerInterceptor}）</li>
 *   <li>为MySQL数据库的分页查询提供自动计数功能</li>
 * </ul>
 *
 * <p><strong>版本说明：</strong>
 * 从3.5.9版本开始，{@code PaginationInnerInterceptor} 被拆分到
 * {@code mybatis-plus-jsqlparser} 包而非原来的 {@code mybatis-plus-extension} 包
 *
 * @see MybatisPlusInterceptor MyBatis-Plus拦截器主体
 * @see PaginationInnerInterceptor 分页SQL生成拦截器
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 创建并配置MyBatis-Plus拦截器.
     *
     * <p>当前配置：
     * <ul>
     *   <li>添加MySQL分页拦截器</li>
     *   <li>自动处理{@code Page<T>}对象的分页逻辑</li>
     * </ul>
     *
     * @return 配置完成的拦截器实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
