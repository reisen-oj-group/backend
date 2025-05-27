package com.oj.backend.controller.user;

import com.oj.backend.dto.request.user.LoginRequestDTO;
import com.oj.backend.dto.response.user.LoginResponseVO;
import com.oj.backend.dto.request.user.UserIdDTO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.response.user.UserResponseVO;
import com.oj.backend.pojo.user.User;
import com.oj.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/user")
    //  返回用户信息
    public ResponseMessage<UserResponseVO> returnUserMessage(@RequestBody UserIdDTO userId){
        return userService.returnUserMessage(userId);
    }

    @PostMapping("/auth/register")
    //  用户注册
    public ResponseMessage<User> insert(@RequestBody User user) {
        return userService.register(user);
    }
    @PostMapping("/auth/login")
    //  用户登录
    public ResponseMessage<LoginResponseVO> login(@RequestBody LoginRequestDTO request){
        return userService.login(request);
    }
}
