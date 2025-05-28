package com.oj.backend.service.user;

import com.oj.backend.dto.request.user.LoginRequestDTO;
import com.oj.backend.dto.request.user.UserIdDTO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.response.user.LoginResponseVO;
import com.oj.backend.dto.response.user.UserResponseVO;
import com.oj.backend.pojo.user.User;

/**
 * 用户服务接口
 * <p>定义用户管理核心业务契约，包含认证授权、用户信息管理等操作</p>
 *
 * <p><b>接口职责：</b></p>
 * <ul>
 *   <li>用户注册与账户管理</li>
 *   <li>用户登录认证与令牌发放</li>
 *   <li>用户基本信息查询</li>
 * </ul>
 */
public interface UserService {
    /**
     * 用户注册
     * <p>创建新用户账户并初始化基本信息</p>
     *
     * @param user 用户注册信息实体，必须包含：
     * <ul>
     *   <li><b>name</b> - 用户名（唯一，支持中文）</li>
     *   <li><b>password</b> - 登录密码（建议前端预先加密）</li>
     * </ul>
     * @return 标准化响应消息，包含：
     * <ul>
     *   <li>成功时：用户基础信息视图对象（不含敏感字段）</li>
     *   <li>失败时：包含错误码和"用户已存在"等提示信息</li>
     * </ul>
     * @apiNote 默认赋予普通用户角色(role=0)，自动记录注册时间
     */
    ResponseMessage<UserResponseVO> register(User user);

    /**
     * 用户登录
     * <p>验证用户凭证并签发访问令牌</p>
     *
     * @param request 登录请求参数，包含：
     * <ul>
     *   <li><b>username</b> - 注册用户名（必填）</li>
     *   <li><b>password</b> - 登录密码（必填，明文传输）</li>
     *   <li><b>remember</b> - 长效会话标识（可选）</li>
     * </ul>
     * @return 标准化响应消息，包含：
     * <ul>
     *   <li>成功时：JWT令牌及用户精简信息</li>
     *   <li>失败时：包含错误码和认证失败原因</li>
     * </ul>
     * @see LoginResponseVO 响应数据结构
     */
    ResponseMessage<LoginResponseVO> login(LoginRequestDTO request);

    /**
     * 查询用户信息
     * <p>获取指定用户的公开信息</p>
     *
     * @param userId 用户标识参数，包含：
     * <ul>
     *   <li><b>user</b> - 用户唯一ID（必填）</li>
     * </ul>
     * @return 标准化响应消息，包含：
     * <ul>
     *   <li>成功时：用户公开信息视图对象</li>
     *   <li>失败时：包含错误码和"用户不存在"提示</li>
     * </ul>
     * @apiNote 返回数据已脱敏，不包含password等字段
     */
    ResponseMessage<UserResponseVO> returnUserMessage(UserIdDTO userId);
}
