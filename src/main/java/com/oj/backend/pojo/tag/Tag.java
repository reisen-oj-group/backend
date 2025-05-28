package com.oj.backend.pojo.tag;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 标签实体类
 * <p>用于题目分类标记，支持多级分类体系</p>
 *
 * <p><b>数据库表：</b>tag</p>
 */
@Data
@TableName("tag")
public class Tag {
    /**
     * 标签ID（自增主键）
     * <p>系统自动生成</p>
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属分类ID
     * <p>指向父级分类，顶级分类时为0或null</p>
     */
    @TableField("classify_id")
    private Integer classifyId;

    /**
     * 标签名称
     * <p><b>约束：</b>唯一不可重复</p>
     * <p>示例值：</p>
     * <ul>
     *   <li>"动态规划"</li>
     *   <li>"图论"</li>
     *   <li>"数据结构"</li>
     * </ul>
     */
    private String name;
}
