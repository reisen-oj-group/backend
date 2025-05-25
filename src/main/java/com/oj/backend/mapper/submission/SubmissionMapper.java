package com.oj.backend.mapper.submission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.backend.pojo.submission.Submission;
import com.oj.backend.utils.result.Result;

import java.util.Collections;
import java.util.List;

public interface SubmissionMapper extends BaseMapper<Submission> {
    // 返回当前用户的提交记录(如果有的话),没有就什么都不返回
    default List<Result> findResultsByProblemsId(List<Integer> ids, Integer user) {
        return user != null
                ? selectList(
                new QueryWrapper<Submission>()
                        .in("problem_id", ids)
                        .eq("user_id", user)
        ).stream().map(Result::new).toList()
                : Collections.emptyList();
    }
}

