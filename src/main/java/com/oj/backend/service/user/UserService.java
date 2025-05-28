package com.oj.backend.service.user;

import com.oj.backend.dto.request.user.LoginRequestDTO;
import com.oj.backend.dto.response.user.LoginResponseVO;
import com.oj.backend.dto.request.user.UserIdDTO;
import com.oj.backend.dto.response.user.UserResponseVO;
import com.oj.backend.pojo.user.User;
import com.oj.backend.dto.response.common.ResponseMessage;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Register response message.
     *
     * @param user the user
     * @return the response message
     */
    ResponseMessage<UserResponseVO> register(User user);

    /**
     * Login response message.
     *
     * @param request the request
     * @return the response message
     */
    ResponseMessage<LoginResponseVO> login(LoginRequestDTO request);

    /**
     * Return user message response message.
     *
     * @param userId the user id
     * @return the response message
     */
    ResponseMessage<UserResponseVO> returnUserMessage(UserIdDTO userId);
}
