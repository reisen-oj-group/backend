package com.oj.backend.dto.response.problem;

import com.oj.backend.dto.request.problem.ProblemFilter;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.pojo.submission.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 题目列表视图对象(VO).
 * <p>用于题目分页查询接口的响应数据结构</p>
 *
 * @see com.oj.backend.controller.problem.ProblemController#getProblemList(ProblemFilter)  关联的Controller方法
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemListVO {
    /**
     * 题目数据列表
     * @see com.oj.backend.pojo.problem.Problem 题目实体类
     */
    private List<Problem> problems;

    /**
     * 对应的用户提交结果列表
     * <p>与problems列表顺序保持一一对应</p>
     * @see com.oj.backend.pojo.submission.Result 提交结果实体类
     */
    private List<Result> results;

    /**
     * 总记录数
     */
    private Integer total;
}
