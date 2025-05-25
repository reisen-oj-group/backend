package com.oj.backend.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @PostMapping("/auth/register")
    public ResponseMessage<User> insert(@RequestBody User user) {
        return userService.register(user);
    }
    @PostMapping("/auth/login")
    public ResponseMessage<Map<String, Object>> login(@RequestBody Map<String, Object> request){
        return userService.login(request);
    }
}
