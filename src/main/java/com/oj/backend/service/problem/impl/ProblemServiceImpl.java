package com.oj.backend.service.problem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oj.backend.dto.request.problem.ProblemFilter;
import com.oj.backend.dto.request.problem.ProblemIdDTO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.response.problem.ProblemListVO;
import com.oj.backend.dto.response.problem.ProblemResponseVO;
import com.oj.backend.mapper.problem.ProblemMapper;
import com.oj.backend.mapper.submission.SubmissionMapper;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.pojo.submission.Result;
import com.oj.backend.service.problem.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 题目服务实现类
 * <p>提供题目相关的业务逻辑实现，包括题目列表查询、题目更新和题目详情获取等功能</p>
 *
 * @see ProblemService 服务接口定义
 */
@Service
public class ProblemServiceImpl implements ProblemService {
    /**
     * 题目数据访问器
     * <p>用于操作题目基础数据的数据库访问接口</p>
     */
    @Autowired
    ProblemMapper problemMapper;
    /**
     * 提交记录数据访问器
     * <p>用于查询用户提交记录相关数据的数据库访问接口</p>
     */
    @Autowired
    SubmissionMapper submissionMapper;

    /**
     * 获取题目列表
     * <p>根据筛选条件分页查询题目列表，并关联查询当前用户的提交结果</p>
     *
     * @param problemFilter 题目筛选条件，包含以下参数：
     *                      <ul>
     *                        <li>page - 当前页码</li>
     *                        <li>pageSize - 每页数量</li>
     *                        <li>minDifficulty - 最小难度值</li>
     *                        <li>maxDifficulty - 最大难度值</li>
     *                        <li>keywords - 搜索关键字</li>
     *                        <li>user - 当前用户ID</li>
     *                      </ul>
     * @return 包含题目列表和用户提交结果的响应消息，数据结构如下：
     * <pre>
     * {@code
     *   "problems": List<Problem>,  // 题目列表
     *   "results": List<Result>,    // 用户提交结果
     *   "total": int                // 总记录数
     * }
     * </pre>
     */
    @Override
    public ResponseMessage<ProblemListVO> getProblemList(ProblemFilter problemFilter) {
        Page<Problem> page = new Page<>(problemFilter.getPage(), problemFilter.getPageSize());

        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();

        buildQueryWrapperForAllList(queryWrapper, problemFilter);

        Page<Problem> problemPage = problemMapper.selectPage(page, queryWrapper);
        List<Problem> problems = problemPage.getRecords();

        // 获取problem的ID
        List<Integer> problemIDs = problems.stream().map(Problem::getId).toList();
        List<Result> results = submissionMapper.findResultsByProblemsId(problemIDs, problemFilter.getUser());

        ProblemListVO response = new ProblemListVO();
        response.setProblems(problems);
        response.setResults(results);
        response.setTotal((int) problemPage.getTotal());

        return ResponseMessage.problemListGetSuccess(response);
    }

    /**
     * 更新题目信息
     * <p>根据题目ID更新题目数据</p>
     *
     * @param id      要更新的题目ID
     * @param problem 包含更新数据的题目对象
     * @return 更新结果响应消息，包含以下可能状态：
     * <ul>
     *   <li>成功 - 返回更新后的题目数据</li>
     *   <li>失败 - 返回错误提示信息</li>
     * </ul>
     */
    @Override
    public ResponseMessage<Problem> problemUpdate(Integer id, Problem problem) {
        problem.setId(id);
        return problemMapper.updateById(problem) > 0 ?
                ResponseMessage.problemUpdateSuccess(problem) : ResponseMessage.problemUpdateError("数据更新失败");
    }

    /**
     * 获取题目详情
     * <p>根据题目ID获取题目完整信息，并包含当前用户对该题目的提交结果</p>
     *
     * @param problemIdDTO 题目ID数据传输对象，包含：
     *                     <ul>
     *                       <li>problem - 题目ID</li>
     *                       <li>user - 当前用户ID</li>
     *                     </ul>
     * @return 包含题目详情和用户提交结果的响应消息
     */
    @Override
    public ResponseMessage<ProblemResponseVO> returnProblemMessage(ProblemIdDTO problemIdDTO) {
        Problem problem = problemMapper.selectById(problemIdDTO.getProblem());
        Result result = submissionMapper.findResultByProblemId(problemIdDTO.getProblem(), problemIdDTO.getUser());

        if (problem == null) {
            return ResponseMessage.error("问题不存在");
        } else {
            return ResponseMessage.success(new ProblemResponseVO(problem, result));
        }
    }

    /**
     * 构建题目列表查询条件
     * <p>根据筛选条件动态构建MyBatis-Plus查询条件</p>
     *
     * @param queryWrapper  MyBatis-Plus查询条件构造器
     * @param problemFilter 题目筛选条件
     */
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

    /**
     * 构建用户提交题目查询条件
     * <p>筛选指定用户已提交过的题目</p>
     *
     * @param queryWrapper  MyBatis-Plus查询条件构造器
     * @param problemFilter 题目筛选条件
     */
    private void buildQueryWrapperForUserSubmission(QueryWrapper<Problem> queryWrapper, ProblemFilter problemFilter) {
        // user提交过的problem查询
        if (problemFilter.getUser() != null) {
            queryWrapper.apply(
                    "id IN (SELECT problem_id FROM submission WHERE user_id = {0})",
                    problemFilter.getUser()
            );
        }
    }

    /**
     * 提取关键字中的数字ID
     * <p>从搜索关键字中提取纯数字部分作为题目ID，只有数字时则按照ID搜索而不是提取某一部分，例如1000只能查到ID为1000的，而不能查到1或10等</p>
     *
     * @param keywords 用户输入的搜索关键字
     * @return 提取的数字ID，若无数字则返回null
     */
    private Integer extractID(String keywords) {
        String num = keywords.replaceAll("[^0-9]", "");
        if (StringUtils.hasText(num)) {
            return Integer.parseInt(num);
        }
        return null;
    }
}
