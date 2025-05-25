package com.oj.backend.config.global;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 注意：PaginationInnerInterceptor从3.5.9版本开始被单独拆分到了`mybatis-plus-jsqlparser`包，而不是`mybatis-plus-extension`
// 该配置用于分页查询的自动计数
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //  PaginationInnerInterceptor：MyBatis-Plus 的分页拦截器，自动将 Page<T> 对象转换为分页 SQL（如 LIMIT）。
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}