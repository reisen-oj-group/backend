package com.oj.backend.utils.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头获取token
        String token = JwtUtil.extractToken(request);

        if (token == null) {
            sendError(response, 500, "No token");
            return false;
        }

        // 验证token有效性
        try {
            Integer id = jwtUtil.parseUserId(token);
            request.setAttribute("userId", id);
            return true;
        } catch (JWTVerificationException e) {
            sendError(response, 500, "Invalid token: " + e.getMessage());
            return false;
        }
    }

    private void sendError(HttpServletResponse response, int code, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(code);
        response.getWriter().write(String.format("{\"code\": %d, \"message\": \"%s\"}", code, message));
    }
}
