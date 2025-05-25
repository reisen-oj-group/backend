package com.oj.backend.service.user;

import com.oj.backend.pojo.user.User;
import com.oj.backend.response.ResponseMessage;

import java.util.Map;
import java.util.Objects;

public interface UserService {
    ResponseMessage<User> register(User user);
    ResponseMessage<Map<String, Object>> login(Map<String, Object> request);
}
