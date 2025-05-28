package com.oj.backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oj.backend.pojo.user.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息响应视图对象(VO).
 * <p>用于用户相关接口的响应数据封装，包含用户公开信息</p>
 *
 * @see com.oj.backend.controller.user.UserController 关联的Controller类
 */
@Data
public class UserResponseVO {
    /**
     * 用户唯一标识
     * @apiNote 示例值：10001
     */
    private Integer id;

    /**
     * 用户名（前端映射为username字段）
     * @apiNote 通过@JsonProperty实现字段别名
     * @see com.fasterxml.jackson.annotation.JsonProperty
     */
    @JsonProperty("username")
    private String name;

    /**
     * 用户角色
     */
    private Integer role;

    /**
     * 注册时间
     * @apiNote ISO8601格式：yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    private LocalDateTime register;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 通过User实体构造VO对象
     * @param user 用户实体对象
     * @see com.oj.backend.pojo.user.User 用户实体类
     */
    public UserResponseVO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole();
        this.register = user.getRegister();
        this.avatar = user.getAvatar();
    }
}

