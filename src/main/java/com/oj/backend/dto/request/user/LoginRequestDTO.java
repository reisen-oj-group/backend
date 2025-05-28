package com.oj.backend.dto.request.user;

import lombok.Data;

/**
 * The type Login request dto.
 */
@Data
public class LoginRequestDTO {
    private String username;
    private String password;
    private Boolean remember;
}
