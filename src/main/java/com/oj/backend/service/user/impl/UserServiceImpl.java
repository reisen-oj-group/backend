package com.oj.backend.service.user.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oj.backend.dto.request.user.LoginRequestDTO;
import com.oj.backend.dto.response.user.LoginResponseVO;
import com.oj.backend.dto.request.user.UserIdDTO;
import com.oj.backend.dto.response.user.UserResponseVO;
import com.oj.backend.mapper.user.UserMapper;
import com.oj.backend.pojo.user.User;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

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

    @Override
    public ResponseMessage<LoginResponseVO> login(LoginRequestDTO request) {
        String username = request.getUsername();
        String password = request.getPassword();
        boolean remember = request.getRemember() != null && request.getRemember();

        User user = authenticate(username, password);

        if (user == null) {
            return ResponseMessage.loginError("用户名或密码错误");
        }

        long rememberTime = remember
                ? System.currentTimeMillis() + 7 * 24 * 3600000L     // 记住我7天
                : System.currentTimeMillis() + 3600000L;              // 否则1个小时

        String token = JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(rememberTime))
                .sign(Algorithm.HMAC256("SecretKey"));

        LoginResponseVO data = new LoginResponseVO();
        data.setToken(token);
        data.setUser(new UserResponseVO(user));
        return ResponseMessage.loginSuccess(data);
    }

    @Override
    public ResponseMessage<UserResponseVO> returnUserMessage(UserIdDTO userId) {
        User user = userMapper.selectById(userId.getUser());
        return user != null
                ? ResponseMessage.success(new UserResponseVO(user))
                : ResponseMessage.error("该用户不存在");
    }

    private User authenticate(String username, String password) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("name", username)
        );
        return user != null && user.getPassword().equals(password)
                ? user : null;
    }
}
