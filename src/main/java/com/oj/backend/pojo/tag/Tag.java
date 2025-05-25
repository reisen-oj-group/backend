package com.oj.backend.pojo.tag;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
@TableName("tag")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("classify_id")
    private Integer classifyId;

    private String name;
}
