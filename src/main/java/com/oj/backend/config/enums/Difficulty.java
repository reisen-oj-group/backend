package com.oj.backend.config.enums;


import com.oj.backend.pojo.problem.Problem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示题目难度级别的定义类。
 * 用于对题目进行难度分级（如简单/中等/困难），支持通过分数区间动态定义难度级别。
 *
 * <p>通常与 {@link Problem} 类关联，用于前端筛选和题目展示的难度标识。
 *
 * <p>使用 Lombok 自动生成基础方法（{@code @Data} {@code @NoArgsConstructor} {@code @AllArgsConstructor}）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Difficulty {
    /**
     * 该难度级别的最低分值（包含）
     * <p>示例值：
     */
    private Integer min;

    /**
     * 该难度级别的最高分值（包含）
     * <p>示例值：
     * <p>与 {@link #min} 共同构成闭区间：[min, max]
     */
    private Integer max;

    /**
     * 难度级别的显示名称
     */
    private String name;
}

