package com.oj.backend.pojo.problem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * The type Problem statement.
 */
@Data
public class ProblemStatement {
    private String background;  // 题目背景

    private String legend;      // 题目描述

    private String formatI;     // 输入格式

    private String formatO;     // 输出格式

    private String hint;        // 提示信息

    @JsonIgnore
    private String note;        // 管理员内部字段

    private List<Example> examples; // 示例列表（直接映射为 List<Example>）
}

//export interface Statement {
//    background?: string // 题目背景
//    legend?: string // 题目描述
//    formatI?: string // 输入格式
//    formatO?: string // 输出格式
//    examples: {
//        // 样例
//        dataI: string
//        dataO: string
//    }[]
//    hint?: string // 提示，含样例解释
//    note?: string // 管理员内部字段
//}