package com.oj.backend.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oj.backend.mapper.user.UserMapper;
import com.oj.backend.pojo.user.User;
import com.oj.backend.response.ResponseMessage;
import com.oj.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseMessage<User> register(User user) {
        // 设置默认角色为普通用户
        user.setRole(0);
        // 设置注册时间为当前时间
        user.setRegister(LocalDateTime.now());

        User exist = userMapper.selectOne(
                new QueryWrapper<User>().eq("name", user.getName())
        );

        if (exist != null) {
            return ResponseMessage.registerError("该用户已存在");
        }

        userMapper.insert(user);

        return ResponseMessage.registerSuccess(user);
    }
}
