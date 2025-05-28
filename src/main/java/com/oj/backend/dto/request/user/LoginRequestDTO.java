package com.oj.backend.dto.request.user;

import lombok.Data;

/**
 * 用户登录请求数据传输对象.
 * <p>用于接收前端登录表单提交的数据</p>
 */
@Data
public class LoginRequestDTO {
    /**
     * 登录用户名（唯一标识）
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 记住登录状态标记
     */
    private Boolean remember;
}
