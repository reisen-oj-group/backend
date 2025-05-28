package com.oj.backend.pojo.submission;

import lombok.Data;

/**
 * 提交记录的测试用例详情
 * <p>用于存储单个测试点的评判结果</p>
 *
 * <p><b>必填字段：</b> id, verdict</p>
 */
@Data
public class SubmissionTestCase {
    /**
     * 测试点ID
     * <p>从1开始递增</p>
     */
    private Integer id;

    /**
     * 判题结果
     * @value "AC", "WA", "TLE", "MLE" 等OJ标准判题结果
     */
    private String verdict;

    /**
     * 用时(ms)
     * <p>该测试点运行耗时</p>
     */
    private Integer time;

    /**
     * 内存占用(KB)
     * <p>该测试点内存消耗</p>
     */
    private Integer memory;

    /**
     * 得分
     * <p>部分分制下有效，满分通常为100</p>
     */
    private Integer score;

    /**
     * 输入数据
     * <p>原始输入内容（可能截断）</p>
     */
    private String input;

    /**
     * 输出数据
     * <p>用户程序的实际输出（可能截断）</p>
     */
    private String output;

    /**
     * 检查器日志
     * <p>自定义检查器的诊断信息</p>
     */
    private String checker;
}
