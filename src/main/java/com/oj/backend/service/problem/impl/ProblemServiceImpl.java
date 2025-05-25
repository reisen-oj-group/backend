package com.oj.backend.service.problem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oj.backend.dto.problem.ProblemIdDTO;
import com.oj.backend.dto.problem.ProblemResponseDTO;
import com.oj.backend.mapper.problem.ProblemMapper;
import com.oj.backend.mapper.submission.SubmissionMapper;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.dto.problem.ProblemListResponse;
import com.oj.backend.response.ResponseMessage;
import com.oj.backend.service.problem.ProblemService;
import com.oj.backend.dto.problem.ProblemFilter;
import com.oj.backend.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    ProblemMapper problemMapper;
    @Autowired
    SubmissionMapper submissionMapper;

    @Override
    public ResponseMessage<ProblemListResponse> getProblemList(ProblemFilter problemFilter) {
        Page<Problem> page = new Page<>(problemFilter.getPage(), problemFilter.getPageSize());

        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();

        buildQueryWrapperForAllList(queryWrapper, problemFilter);

        Page<Problem> problemPage = problemMapper.selectPage(page, queryWrapper);
        List<Problem> problems = problemPage.getRecords();

        // 获取problem的ID
        List<Integer> problemIDs = problems.stream().map(Problem::getId).toList();
        List<Result> results = submissionMapper.findResultsByProblemsId(problemIDs, problemFilter.getUser());

        ProblemListResponse response = new ProblemListResponse();
        response.setProblems(problems);
        response.setResults(results);
        response.setTotal((int) problemPage.getTotal());

        return ResponseMessage.problemListGetSuccess(response);
    }

    @Override
    public ResponseMessage<Problem> problemUpdate(Integer id, Problem problem) {
        problem.setId(id);
        return problemMapper.updateById(problem) > 0 ?
                ResponseMessage.problemUpdateSuccess(problem) : ResponseMessage.problemUpdateError("数据更新失败");
    }

    @Override
    public ResponseMessage<ProblemResponseDTO> returnProblemMessage(ProblemIdDTO problemIdDTO) {
        Problem problem = problemMapper.selectById(problemIdDTO.getProblem());
        Result result = submissionMapper.findResultByProblemId(problemIdDTO.getProblem(), problemIdDTO.getUser());
        return ResponseMessage.success(new ProblemResponseDTO(problem, result));
    }

    private void buildQueryWrapperForAllList(QueryWrapper<Problem> queryWrapper, ProblemFilter problemFilter) {
        // difficulty查询
        if (problemFilter.getMinDifficulty() != null || problemFilter.getMaxDifficulty() != null) {
            queryWrapper.ge(problemFilter.getMinDifficulty() != null, "difficulty", problemFilter.getMinDifficulty())
                    .le(problemFilter.getMaxDifficulty() != null, "difficulty", problemFilter.getMaxDifficulty());
        }

        // 关键字查询
        // 模糊查询，只要中英文标题中存在就返回
        // 注意下面这段代码中$.{0}会被替换成$.?，报错
        // queryWrapper.and(w -> {
        //                Arrays.asList("en-US", "zh-CN").forEach(
        //                        lang -> {
        //                            w.or().apply("title->>'$.${0}' LIKE {1}", lang, "%" + problemFilter.getKeywords() + "%");
        //                        }
        //                );
        //            }).or().apply("id = {0}", extractID(problemFilter.getKeywords()));

        // zh-CN要加上双引号，否则-会被识别为减号，->>表示返回的数据不带""，->表示带引号返回
        if (StringUtils.hasText(problemFilter.getKeywords())) {
            queryWrapper.and(w ->
                    w.or().apply("title->>'$.\"zh-CN\"' LIKE {0}", "%" + problemFilter.getKeywords() + "%")
                            .or().apply("title->>'$.\"en-US\"' LIKE {0}", "%" + problemFilter.getKeywords() + "%")
            ).or().apply("id = {0}", extractID(problemFilter.getKeywords()));
        }
    }

    private void buildQueryWrapperForUserSubmission(QueryWrapper<Problem> queryWrapper, ProblemFilter problemFilter) {
        // user提交过的problem查询
        if (problemFilter.getUser() != null) {
            queryWrapper.apply(
                    "id IN (SELECT problem_id FROM submission WHERE user_id = {0})",
                    problemFilter.getUser()
            );
        }
    }

    // 将除数字外的字符全部去除，输入关键字中包含题目ID也可查询到
    private Integer extractID(String keywords) {
        String num = keywords.replaceAll("[^0-9]", "");
        if (StringUtils.hasText(num)) {
            return Integer.parseInt(num);
        }
        return null;
    }
}
