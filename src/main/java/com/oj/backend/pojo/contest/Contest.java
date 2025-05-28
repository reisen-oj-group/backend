package com.oj.backend.pojo.contest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 竞赛信息实体类
 * <p>映射数据库 contest 表，包含竞赛基础信息和时间配置</p>
 *
 * <p><b>字段说明：</b></p>
 * <ul>
 *   <li>id: 主键自增长</li>
 *   <li>title: 竞赛标题</li>
 *   <li>summary: 竞赛简介</li>
 *   <li>description: 竞赛详细说明</li>
 *   <li>difficulty: 难度等级(数值型)</li>
 *   <li>status: 竞赛状态</li>
 *   <li>rule: 竞赛规则说明</li>
 *   <li>时间类字段均使用LocalDateTime类型</li>
 * </ul>
 *
 * @see TableName 表名映射注解
 * @see TableId 主键标识注解
 */
@Data
@TableName("contest")
public class Contest {
    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 竞赛标题 */
    private String title;

    /** 竞赛简介(短描述) */
    private String summary;

    /** 竞赛详细说明 */
    private String description;

    /** 难度级别 */
    private Integer difficulty;

    /** 状态(未开始/进行中/已结束) */
    private String status;

    /** 开始时间(ISO格式) */
    @TableField("start_time")
    private LocalDateTime startTime;

    /** 结束时间(ISO格式) */
    @TableField("end_time")
    private LocalDateTime endTime;

    /** 竞赛规则说明 */
    private String rule;

    /** 记录创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 最后更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}

