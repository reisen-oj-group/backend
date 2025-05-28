package com.oj.backend.pojo.problem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 题目详细描述结构
 * <p>与前端接口 Statement 完全对齐，字段说明见：</p>
 *
 * <pre>{@code
 * // 前端接口定义
 * export interface Statement {
 *     background?: string  // 题目背景
 *     legend?: string      // 题目描述
 *     formatI?: string     // 输入格式
 *     formatO?: string     // 输出格式
 *     examples: {          // 样例列表
 *         dataI: string
 *         dataO: string
 *     }[]
 *     hint?: string        // 提示信息（含样例解释）
 *     note?: string        // 管理员内部字段（不序列化到前端）
 * }
 * }</pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // 忽略null字段
public class ProblemStatement {
    /** 题目背景（可选） */
    private String background;

    /** 题目描述（可选） */
    private String legend;

    /** 输入格式说明（可选） */
    private String formatI;

    /** 输出格式说明（可选） */
    private String formatO;

    /** 提示信息（含样例解释，可选） */
    private String hint;

    /** 管理员内部备注（不暴露给前端） */
    @JsonIgnore
    private String note;

    /**
     * 示例列表（必填）
     * <p>每个示例包含：</p>
     * <ul>
     *   <li>dataI: 输入样例</li>
     *   <li>dataO: 输出样例</li>
     * </ul>
     */
    private List<Example> examples;
}
