package com.oj.backend.dto.user;

import com.oj.backend.pojo.user.User;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private User user;
}
