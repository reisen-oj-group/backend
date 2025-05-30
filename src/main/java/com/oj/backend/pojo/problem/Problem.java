package com.oj.backend.pojo.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;




/**
 * 题目信息实体类
 * <p>映射数据库 problem 表，包含题目基础配置和多语言内容</p>
 *
 * <p><b>特殊字段处理说明：</b></p>
 * <ul>
 *   <li>autoResultMap=true: 解决BLOB类型字段反序列化问题</li>
 *   <li>JacksonTypeHandler: 处理JSON结构的Map类型字段</li>
 * </ul>
 */
// 注意，此处autoResultMap = true很关键，不知道为什么查询时候返回的statement和title都是BLOB，加上他之后虽然还是，但是已经可以正常返回了
@Data
@TableName(value = "problem", autoResultMap = true)
public class Problem {
    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 题目类型
     * @value 'traditional' | 'interactive'
     */
    private String type;

    /**
     * 题目状态
     * @value 'public' | 'private' | 'contest'
     */
    private String status;

    /**
     * 时间限制(ms)
     */
    // @JsonProperty("limit_time")
    @TableField("limit_time")
    private Integer limitTime;

    /**
     * 内存限制(KB)
     */
    // @JsonProperty("limit_memory")
    @TableField("limit_memory")
    private Integer limitMemory;

    /**
     * 多语言题目描述
     * <p>Key格式: 语言代码(如zh-CN/en-US)</p>
     * <p><b>注意：</b>必须使用JacksonTypeHandler处理JSON转换</p>
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, ProblemStatement> statements;

    /**
     * 多语言标题
     * <p>Key格式: 语言代码(如zh-CN/en-US)</p>
     * <p><b>BLOB解决方案：</b>autoResultMap=true启用类型自动映射</p>
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, String> title;

    /** 通过次数 */
    // @JsonProperty("count_correct")
    @TableField("count_correct")
    private Integer countCorrect;

    /** 提交次数 */
    // @JsonProperty("count_total")
    @TableField("count_total")
    private Integer countTotal;

    /** 难度系数 */
    private Integer difficulty;

    @TableField("create_at")
    private LocalDateTime createdAt;

    @TableField("update_at")
    private LocalDateTime updatedAt;

    @TableField("has_config")
    private boolean hasConfig;

    @JsonProperty("hasTestdata")
    @TableField("has_test_data")
    private boolean hasTestData;
}

