package com.oj.backend.dto.response.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oj.backend.pojo.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseVO {
    private Integer id;

    @JsonProperty("username")   // 前端username字段映射为name
    private String name;

    private Integer role;

    private LocalDateTime register;

    private String avatar;

    public UserResponseVO(User user){
        this.avatar = user.getAvatar();
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole();
        this.register = user.getRegister();
    }
}
