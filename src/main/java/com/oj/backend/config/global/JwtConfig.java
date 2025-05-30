package com.oj.backend.config.global;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    /**
     * 加密密钥
     */
    private String secret = "SecretKey"; // 默认值
    /**
     * 默认Token有效期（毫秒）
     */
    private long expire = 3600000;  // 1小时
    /**
     * 记住我模式下的Token有效期（毫秒）
     */
    private long rememberExpire = 604800000; //7 天
    /**
     * Token签发者
     */
    private String issuer = "oj_system";
}
