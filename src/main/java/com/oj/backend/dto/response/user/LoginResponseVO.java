package com.oj.backend.dto.response.user;

import lombok.Data;

/**
 * The type Login response vo.
 */
@Data
public class LoginResponseVO {
    private String token;
    private UserResponseVO user;
}
