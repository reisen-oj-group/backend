package com.oj.backend.pojo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * <p>对应数据库 users 表，存储系统用户基础信息</p>
 *
 * <p><b>注意：</b>密码字段仅用于接收前端数据，不参与序列化输出</p>
 */
@Data
@TableName("users")
public class User {
    /**
     * 用户ID（自增主键）
     * <p>系统自动生成</p>
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     * <p><b>映射规则：</b>前端字段 username 对应数据库 name 字段</p>
     */
    @TableField("name")
    @JsonProperty("username")
    private String name;

    /**
     * 密码
     */
    @JsonProperty("password")
    private String password;

    /**
     * 角色权限标识
     */
    private Integer role;

    /** 注册时间 */
    private LocalDateTime register;

    /**
     * 头像URL
     * <p><b>格式示例：</b></p>
     * <ul>
     *   <li>绝对路径：http://example.com/avatar.jpg</li>
     *   <li>相对路径：/static/avatar/default.png</li>
     * </ul>
     */
    private String avatar;
}
