package com.oj.backend.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oj.backend.dto.user.LoginRequestDTO;
import com.oj.backend.dto.user.LoginResponseDTO;
import com.oj.backend.dto.user.UserIdDTO;
import com.oj.backend.response.ResponseMessage;
import com.oj.backend.mapper.user.UserMapper;
import com.oj.backend.pojo.user.User;
import com.oj.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/user")
    //  返回用户信息
    public ResponseMessage<User> returnUserMessage(@RequestBody UserIdDTO userId){
        return userService.returnUserMessage(userId);
    }

    @PostMapping("/auth/register")
    //  用户注册
    public ResponseMessage<User> insert(@RequestBody User user) {
        return userService.register(user);
    }
    @PostMapping("/auth/login")
    //  用户登录
    public ResponseMessage<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){
        return userService.login(request);
    }
}
