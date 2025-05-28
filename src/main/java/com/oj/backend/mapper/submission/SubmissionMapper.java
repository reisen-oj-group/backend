package com.oj.backend.mapper.submission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.backend.pojo.submission.Result;
import com.oj.backend.pojo.submission.Submission;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;
import java.util.List;


/**
 * 提交记录数据访问接口
 * <p>提供对用户题目提交记录(Submission)的基础CRUD操作及业务扩展方法</p>
 *
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper MyBatis-Plus基础接口
 * @see com.oj.backend.pojo.submission.Submission 提交记录实体类
 */
@Mapper
public interface SubmissionMapper extends BaseMapper<Submission> {

    /**
     * 批量查询指定题目的用户提交结果
     * <p>当用户ID有效时，返回该用户在指定题目集合中的最新提交记录转换的结果对象列表</p>
     *
     * @param ids  题目ID集合 (required)
     * @param user 用户ID (nullable)
     * @return 结果对象列表（可能为空但不会为null）
     * @see Result 提交结果包装类
     */
    default List<Result> findResultsByProblemsId(List<Integer> ids, Integer user) {
        return user != null
                ? selectList(
                new QueryWrapper<Submission>()
                        .in("problem_id", ids)
                        .eq("user_id", user)
        ).stream()
                .map(Result::new)
                .toList()
                : Collections.emptyList();
    }

    /**
     * 查询单个题目的用户最新提交结果
     * <p>按提交时间降序获取指定用户在该题目下的最后一次提交记录</p>
     *
     * @param id   题目ID (required)
     * @param user 用户ID (required)
     * @return 结果包装对象（无记录时返回null）
     * @throws IllegalArgumentException 当用户ID为null时抛出
     */
    default Result findResultByProblemId(Integer id, Integer user) {
        Submission submission = selectOne(
                new QueryWrapper<Submission>()
                        .eq("problem_id", id)
                        .eq("user_id", user)
                        .orderByDesc("submission_time")
                        .last("LIMIT 1")
        );
        return submission != null ? new Result(submission) : null;
    }
}


