package com.oj.backend.dto.response.user;

import com.oj.backend.dto.request.user.LoginRequestDTO;
import lombok.Data;

/**
 * 登录响应视图对象(VO).
 * <p>用于用户登录接口的响应数据结构，包含认证令牌和用户基础信息</p>
 *
 * @see com.oj.backend.controller.user.UserController#login(LoginRequestDTO)  关联的Controller方法
 */
@Data
public class LoginResponseVO {
    /**
     * JWT认证令牌
     * <p>用于后续接口调用的身份验证，有效期为${token.expire-time}小时</p>
     * @see com.auth0.jwt.JWT JWT配置
     */
    private String token;

    /**
     * 用户基础信息
     * @see com.oj.backend.dto.response.user.UserResponseVO 用户信息视图对象
     */
    private UserResponseVO user;
}
