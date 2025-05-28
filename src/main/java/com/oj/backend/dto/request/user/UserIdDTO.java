package com.oj.backend.dto.request.user;

import lombok.Data;

/**
 * 用户标识数据传输对象.
 * <p>用于封装用户唯一标识符的简单包装类</p>
 *
 * @see com.oj.backend.pojo.user.User 关联的用户实体类
 */
@Data
public class UserIdDTO {
    /**
     * 用户唯一标识符
     * <p>对应数据库用户表主键ID</p>
     */
    private Integer user;
}
