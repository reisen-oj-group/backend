package com.oj.backend.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oj.backend.dto.request.user.LoginRequestDTO;
import com.oj.backend.dto.request.user.UserIdDTO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.response.user.LoginResponseVO;
import com.oj.backend.dto.response.user.UserResponseVO;
import com.oj.backend.mapper.user.UserMapper;
import com.oj.backend.pojo.user.User;
import com.oj.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oj.backend.utils.jwt.JwtUtil;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 * <p>提供用户相关的核心业务逻辑实现，包括注册、登录和用户信息查询等功能</p>
 *
 * <p><b>主要功能：</b></p>
 * <ul>
 *   <li>用户注册与校验</li>
 *   <li>用户登录认证与JWT令牌发放</li>
 *   <li>用户基础信息查询</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    /**
     * 用户数据访问器
     * <p>用于操作用户数据的MyBatis Plus Mapper接口</p>
     */
    @Autowired
    UserMapper userMapper;

    private final JwtUtil jwtUtil;

    /**
     * 用户注册
     * <p>处理新用户注册请求，默认赋予普通用户角色(role=0)</p>
     *
     * @param user 包含注册信息的用户实体，必须包含：
     *             <ul>
     *               <li><b>name</b> - 用户名（唯一标识）</li>
     *               <li><b>password</b> - 密码（明文存储，实际生产环境应加密）</li>
     *             </ul>
     * @return 注册结果响应消息：
     * <ul>
     *   <li>成功 - 返回用户基本信息（不含密码）</li>
     *   <li>失败 - 返回"用户已存在"错误</li>
     * </ul>
     * @implNote 自动设置register时间为当前系统时间
     */
    @Override
    public ResponseMessage<UserResponseVO> register(User user) {
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

        return ResponseMessage.registerSuccess(new UserResponseVO(user));
    }

    /**
     * 用户登录
     * <p>处理用户登录请求，验证通过后签发JWT令牌</p>
     *
     * @param request 登录请求数据传输对象，包含：
     *                <ul>
     *                  <li><b>username</b> - 用户名（必填）</li>
     *                  <li><b>password</b> - 密码（必填）</li>
     *                  <li><b>remember</b> - 记住我选项（可选）</li>
     *                </ul>
     * @return 登录结果响应消息：
     * <ul>
     *   <li>成功 - 返回包含JWT令牌和用户信息的响应体</li>
     *   <li>失败 - 返回"用户名或密码错误"提示</li>
     * </ul>
     * @implSpec 令牌有效期：
     * <ul>
     *   <li>记住我模式：7天</li>
     *   <li>普通模式：1小时</li>
     * </ul>
     *
     * <p><b>安全说明：</b> 使用HMAC256算法签名，密钥为"SecretKey"（生产环境应配置加密密钥）</p>
     */
    @Override
    public ResponseMessage<LoginResponseVO> login(LoginRequestDTO request) {
        String username = request.getUsername();
        String password = request.getPassword();
        boolean remember = request.getRemember() != null && request.getRemember();

        User user = authenticate(username, password);

        if (user == null) {
            return ResponseMessage.loginError("用户名或密码错误");
        }

        // 生成token
        String token = jwtUtil.generateToken(user, remember);

        LoginResponseVO data = new LoginResponseVO();
        data.setToken(token);
        data.setUser(new UserResponseVO(user));
        return ResponseMessage.loginSuccess(data);
    }

    /**
     * 获取用户信息
     * <p>根据用户ID查询用户公开信息</p>
     *
     * @param userId 用户ID数据传输对象，包含：
     *               <ul>
     *                 <li><b>user</b> - 要查询的用户ID（必填）</li>
     *               </ul>
     * @return 查询结果响应消息：
     * <ul>
     *   <li>成功 - 返回用户公开信息视图对象</li>
     *   <li>失败 - 返回"用户不存在"错误</li>
     * </ul>
     * @apiNote 返回数据中不包含密码等敏感字段
     */
    @Override
    public ResponseMessage<UserResponseVO> returnUserMessage(UserIdDTO userId) {
        User user = userMapper.selectById(userId.getUser());
        return user != null
                ? ResponseMessage.success(new UserResponseVO(user))
                : ResponseMessage.error("该用户不存在");
    }

    /**
     * 用户认证
     * <p>内部认证方法，验证用户名密码是否匹配</p>
     *
     * @param username 待验证的用户名
     * @param password 待验证的密码（明文）
     * @return 认证通过返回用户实体，否则返回null
     */
    private User authenticate(String username, String password) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("name", username)
        );
        return user != null && user.getPassword().equals(password)
                ? user : null;
    }
}
