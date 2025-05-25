package com.oj.backend.dto.response.user;

import com.oj.backend.pojo.user.User;
import lombok.Data;

@Data
public class LoginResponseVO {
    private String token;
    private User user;
}
