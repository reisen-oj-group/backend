package com.oj.backend.dto.response.problem;

import com.oj.backend.dto.request.problem.ProblemIdDTO;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.pojo.submission.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目详情响应视图对象(VO).
 * <p>用于题目详情查询接口的响应数据结构，包含题目信息和用户提交结果</p>
 *
 * @see com.oj.backend.controller.problem.ProblemController#returnProblemMessage(ProblemIdDTO)  关联的Controller方法
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemResponseVO {
    /**
     * 题目详细信息
     * @see com.oj.backend.pojo.problem.Problem 题目实体类
     */
    private Problem problem;

    /**
     * 用户最新提交结果（可选）
     * <p>可能为null（当用户尚未提交过该题目时）</p>
     * @see com.oj.backend.pojo.submission.Result 提交结果实体类
     */
    private Result result;
}
