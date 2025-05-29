package com.oj.backend.controller.problem;

import com.oj.backend.dto.request.problem.ProblemFilter;
import com.oj.backend.dto.request.problem.ProblemIdDTO;
import com.oj.backend.dto.request.submission.SubmissionDTO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.response.problem.ProblemListVO;
import com.oj.backend.dto.response.problem.ProblemResponseVO;
import com.oj.backend.dto.response.submission.RecordVO;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.service.problem.ProblemService;
import com.oj.backend.service.submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 题目管理API控制器.
 *
 * <p><strong>功能说明：</strong>
 * <ul>
 *   <li>提供题目信息查询、修改和列表获取功能</li>
 *   <li>统一使用 {@code /api/problem} 作为基础路径</li>
 *   <li>默认启用跨域支持</li>
 * </ul>
 *
 * @see RestController Spring REST控制器
 * @see CrossOrigin 跨域支持注解
 */
@RestController
@RequestMapping("/api/problem")
@CrossOrigin
public class ProblemController {
    /**
     * 题目服务依赖注入.
     *
     * <p>通过Spring自动注入 {@link ProblemService} 业务逻辑实现
     */
    @Autowired
    ProblemService problemService;

    /**
     * The Submission service.
     */
    @Autowired
    SubmissionService submissionService;

    /**
     * 获取题目详细信息.
     *
     * <p><strong>请求说明：</strong>
     * <ul>
     *   <li>HTTP方法：POST</li>
     *   <li>路径：/api/problem</li>
     *   <li>Content-Type：application/json</li>
     * </ul>
     *
     * @param problemIdDTO 包含题目ID的请求体
     *        {@link ProblemIdDTO} 需包含有效的题目标识
     * @return 统一响应格式 {@link ResponseMessage} 包装的题目详情
     *         {@link ProblemResponseVO} 包含完整题目信息
     * @see PostMapping POST请求映射
     */
    @PostMapping("")
    public ResponseMessage<ProblemResponseVO> returnProblemMessage(@RequestBody ProblemIdDTO problemIdDTO) {
        return problemService.returnProblemMessage(problemIdDTO);
    }

    /**
     * 更新题目信息.
     *
     * <p><strong>路径参数说明：</strong>
     * <ul>
     *   <li>{@code problemId} 需要与请求体中的题目ID保持一致</li>
     * </ul>
     *
     * @param id 路径参数中的题目ID
     *        {@code @PathVariable} 注解确保参数从URL获取
     * @param problem 待更新的题目信息
     *        {@link Problem} 包含需要更新的字段
     * @return 统一响应格式 {@link ResponseMessage} 包装的更新后题目
     */
    @PostMapping("/{problemId}/edit")
    public ResponseMessage<Problem> updateProblem(
            @PathVariable("problemId") Integer id,
            @RequestBody Problem problem
    ) {
        return problemService.problemUpdate(id, problem);
    }

    /**
     * 获取题目列表（带过滤条件）.
     *
     * <p><strong>过滤条件说明：</strong>
     * <ul>
     *   <li>支持分页、分类等多维度筛选</li>
     *   <li>具体过滤字段参见 {@link ProblemFilter}</li>
     * </ul>
     *
     * @param problemFilter 列表筛选条件
     *        {@link ProblemFilter} 包含分页、排序等参数
     * @return 统一响应格式 {@link ResponseMessage} 包装的题目列表
     *         {@link ProblemListVO} 包含分页信息和题目数据
     */
    @PostMapping("/list")
    public ResponseMessage<ProblemListVO> getProblemList(@RequestBody ProblemFilter problemFilter) {
        return problemService.getProblemList(problemFilter);
    }

    /**
     * Judge submission response message.
     *
     * @param submissionDTO the submission info
     * @return the response message
     */
    @PostMapping("/submit")
    public ResponseMessage<RecordVO> judgeSubmission(@RequestBody SubmissionDTO submissionDTO){
        return submissionService.judgeSubmission(submissionDTO);
    }
}
