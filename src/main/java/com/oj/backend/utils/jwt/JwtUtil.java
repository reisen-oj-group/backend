package com.oj.backend.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oj.backend.config.global.JwtConfig;
import com.oj.backend.pojo.user.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtConfig jwtConfig;

    /**
     * 生成Token（包含用户核心信息）
     *
     * @param user     用户实体
     * @param remember 是否记住我
     * @return 签发的Token
     */
    public String generateToken(User user, boolean remember) {
        long expireTime = remember
                ? System.currentTimeMillis() + jwtConfig.getRememberExpire()
                : System.currentTimeMillis() + jwtConfig.getExpire();

        return JWT.create()
                .withIssuer(jwtConfig.getIssuer())
                .withSubject(user.getId().toString())          // 用户ID作为主体
                .withClaim("name", user.getName())        // 用户名
                .withClaim("role", user.getRole())        // 用户角色
                .withIssuedAt(new Date())                     // 签发时间
                .withExpiresAt(new Date(expireTime))    // 过期时间
                .sign(Algorithm.HMAC256(jwtConfig.getSecret()));    // 签名算法
    }

    /**
     * 解析Token获取用户ID
     *
     * @param token JWT Token
     * @return 用户ID
     * @throws JWTVerificationException 如果Token无效
     */
    public Integer parseUserId(String token) throws JWTVerificationException {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret()))
                .withIssuer(jwtConfig.getIssuer())
                .build()
                .verify(token);

        return Integer.parseInt(jwt.getSubject());
    }

    /**
     * 获取Token剩余有效期（秒）
     */
    public long getRemainingSeconds(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return (jwt.getExpiresAt().getTime() - System.currentTimeMillis()) / 1000;
    }

    /**
     * 从请求头获取token
     */
    public static String extractToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")){
            return null;
        }
        return header.substring(7); // 去掉 "Bearer " 前缀
    }
}
