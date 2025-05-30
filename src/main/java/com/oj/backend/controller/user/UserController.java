package com.oj.backend.controller.user;

import com.oj.backend.dto.request.user.LoginRequestDTO;
import com.oj.backend.dto.request.user.UserIdDTO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.response.user.LoginResponseVO;
import com.oj.backend.dto.response.user.UserResponseVO;
import com.oj.backend.pojo.user.User;
import com.oj.backend.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理API控制器.
 *
 * <p><strong>功能说明：</strong>
 * <ul>
 *   <li>提供用户信息查询、注册、登录功能</li>
 *   <li>统一使用 {@code /api} 作为基础路径</li>
 *   <li>认证相关接口使用 {@code /auth} 子路径</li>
 *   <li>默认启用跨域支持</li>
 * </ul>
 *
 * @see RestController Spring REST控制器
 * @see CrossOrigin 跨域支持注解
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    /**
     * 用户服务依赖注入.
     *
     * <p>通过Spring自动注入 {@link UserService} 业务逻辑实现
     */
    @Autowired
    UserService userService;

    /**
     * 获取用户详细信息.
     *
     * <p><strong>请求说明：</strong>
     * <ul>
     *   <li>HTTP方法：POST</li>
     *   <li>路径：/api/user</li>
     *   <li>Content-Type：application/json</li>
     * </ul>
     *
     * @param userId 包含用户ID的请求体
     *        {@link UserIdDTO} 需包含有效的用户标识
     * @return 统一响应格式 {@link ResponseMessage} 包装的用户详情
     *         {@link UserResponseVO} 包含完整用户信息
     * @see PostMapping POST请求映射
     */
    @PostMapping("/user")
    public ResponseMessage<UserResponseVO> returnUserMessage(@RequestBody UserIdDTO userId) {
        return userService.returnUserMessage(userId);
    }

    /**
     * 用户注册接口.
     *
     * <p><strong>业务规则：</strong>
     * <ul>
     *   <li>用户名必须唯一</li>
     *   <li>密码需符合安全策略</li>
     * </ul>
     *
     * @param user 注册用户信息
     *        {@link User} 必须包含用户名、密码等必填字段
     * @return 统一响应格式 {@link ResponseMessage} 包装的注册结果
     *         {@link UserResponseVO} 包含注册成功的用户信息
     */
    @PostMapping("/auth/register")
    public ResponseMessage<UserResponseVO> insert(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 用户登录认证.
     *
     * <p><strong>安全说明：</strong>
     * <ul>
     *   <li>采用加密传输密码</li>
     *   <li>返回JWT令牌用于后续认证</li>
     * </ul>
     *
     * @param request 登录请求参数
     *        {@link LoginRequestDTO} 需包含有效的用户名和密码
     * @return 统一响应格式 {@link ResponseMessage} 包装的登录结果
     *         {@link LoginResponseVO} 包含访问令牌和用户基本信息
     */
    @PostMapping("/auth/login")
    public ResponseMessage<LoginResponseVO> login(@RequestBody LoginRequestDTO request) {
        return userService.login(request);
    }

    @PostMapping("/auth/me")    // 用于网页刷新时返回当前用户
    public ResponseMessage<UserResponseVO> returnUser(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        return  userService.returnUserMessage(new UserIdDTO(userId));
    }
}

