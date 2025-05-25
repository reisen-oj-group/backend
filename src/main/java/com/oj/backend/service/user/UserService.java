package com.oj.backend.service.user;

import com.oj.backend.pojo.user.User;
import com.oj.backend.response.ResponseMessage;

public interface UserService {
    ResponseMessage<User> register(User user);
}
