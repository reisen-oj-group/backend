package com.oj.backend.service.user;

import com.oj.backend.dto.request.user.LoginRequestDTO;
import com.oj.backend.dto.response.user.LoginResponseVO;
import com.oj.backend.dto.request.user.UserIdDTO;
import com.oj.backend.dto.response.user.UserResponseVO;
import com.oj.backend.pojo.user.User;
import com.oj.backend.dto.response.common.ResponseMessage;

public interface UserService {
    ResponseMessage<User> register(User user);
    ResponseMessage<LoginResponseVO> login(LoginRequestDTO request);

    ResponseMessage<UserResponseVO> returnUserMessage(UserIdDTO userId);
}
