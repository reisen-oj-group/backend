package com.oj.backend.pojo.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Map;
import java.util.Objects;

/**
 * The type Problem.
 */
// 注意，此处autoResultMap = true很关键，不知道为什么查询时候返回的statement和title都是BLOB，加上他之后虽然还是，但是已经可以正常返回了
@Data
@TableName(value = "problem", autoResultMap = true)
public class Problem {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String type;        // 'traditional' | 'interactive' // 传统题、交互题

    private String status;      // 'public' | 'private' | 'contest'

    @JsonProperty("limit_time")
    @TableField("limit_time")
    private Integer limitTime;

    @JsonProperty("limit_memory")
    @TableField("limit_memory")
    private Integer limitMemory;

    @TableField(typeHandler = JacksonTypeHandler.class)     // Mybatis-plus自带管理 JSON 字段，实现序列化与反序列化
    private Map<String, ProblemStatement> statements;       // 键是语言en-US或者zh-CN

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, String> title;

    @JsonProperty("count_correct")
    @TableField("count_correct")
    private Integer countCorrect;

    @JsonProperty("count_total")
    @TableField("count_total")
    private Integer countTotal;

    private Integer difficulty;
}
