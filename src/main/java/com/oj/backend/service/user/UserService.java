package com.oj.backend.service.user;

import com.oj.backend.dto.user.LoginRequestDTO;
import com.oj.backend.dto.user.LoginResponseDTO;
import com.oj.backend.dto.user.UserIdDTO;
import com.oj.backend.pojo.user.User;
import com.oj.backend.response.ResponseMessage;

import java.util.Map;
import java.util.Objects;

public interface UserService {
    ResponseMessage<User> register(User user);
    ResponseMessage<LoginResponseDTO> login(LoginRequestDTO request);

    ResponseMessage<User> returnUserMessage(UserIdDTO userId);
}
