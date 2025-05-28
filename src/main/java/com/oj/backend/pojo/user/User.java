package com.oj.backend.pojo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type User.
 */
@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    @JsonProperty("username")   // 前端username字段映射为name
    private String name;

    @JsonProperty("password")
    private String password;    // 让Jackson忽略此字段

    private Integer role;

    private LocalDateTime register;

    private String avatar;
}
