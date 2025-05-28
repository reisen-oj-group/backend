package com.oj.backend.pojo.contest;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type Contest.
 */
@Data
@TableName("contest")
public class Contest {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String summary;

    private String description;

    private Integer difficulty;

    private String status;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    private String rule;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
