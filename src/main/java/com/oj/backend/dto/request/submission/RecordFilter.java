package com.oj.backend.dto.request.submission;

import com.oj.backend.pojo.submission.Submission;
import lombok.Data;


/**
 * 提交记录查询过滤器类
 * <p>
 * 用于对提交记录({@code Submission})进行分页和条件筛选，
 * 支持按编程语言、题目、判题结果、用户等维度过滤。
 * </p>
 *
 *
 * @author Getsumii
 * @version 1.0
 * @see Submission
 * @since 2025-05
 */
@Data
public class RecordFilter {
    /**
     * 编程语言过滤条件
     * <p>
     * 可选值示例：
     * <ul>
     *   <li>"java" - Java语言</li>
     *   <li>"cpp" - C++语言</li>
     *   <li>"python" - Python语言</li>
     * </ul>
     * 为空表示不限制语言
     */
    private String lang;

    /**
     * 题目ID过滤条件
     * <p>
     * 需与数据库中的题目ID({@code problem_id})匹配，
     * 为空表示不限制题目。
     */
    private Integer problem;

    /**
     * 判题结果过滤条件
     * <p>
     * 可选值示例：
     * <ul>
     *   <li>"AC" - 答案正确</li>
     *   <li>"WA" - 答案错误</li>
     *   <li>"TLE" - 时间超限</li>
     *   <li>"UKE" - 未知错误</li>
     * </ul>
     * 为空表示不限制判题结果。
     */
    private String verdict;

    /**
     * 用户过滤条件
     * <p>
     * 支持以下两种形式：
     * <ol>
     *   <li>用户ID - 精确匹配用户编号(如: 123)</li>
     *   <li>用户名 - 模糊匹配用户名称(如: "john")</li>
     * </ol>
     * 为空表示不限制用户。
     */
    private String user;

    /**
     * 当前页码
     * <p>
     * 默认值: 1 (第一页)
     * 必须大于等于1。
     */
    private Integer page = 1;

    /**
     * 每页记录数
     * <p>
     * 默认值: 50
     */
    private Integer pageSize = 50;
}