package com.oj.backend.dto.response.user;

import lombok.Data;

@Data
public class LoginResponseVO {
    private String token;
    private UserResponseVO user;
}
