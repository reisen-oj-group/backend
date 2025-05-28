package com.oj.backend.service.submission;

import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.pojo.submission.Submission;

/**
 * 提交评测服务接口
 * <p>
 * 定义了处理用户代码提交评测的核心方法，负责将用户提交的代码进行编译、
 * 测试用例执行和结果判定，并返回完整的评测结果。
 * </p>
 */
public interface SubmissionService {
    /**
     * 评测用户提交的代码
     *
     * @param submission 提交实体对象，包含：
     *                   <ul>
     *                     <li>problemId - 题目ID</li>
     *                     <li>code - 用户提交的源代码</li>
     *                     <li>lang - 编程语言类型</li>
     *                   </ul>
     * @return 包含评测结果的响应消息：
     *         <ul>
     *           <li>成功时返回完整评测结果(Submission对象)</li>
     *           <li>失败时返回错误信息</li>
     *         </ul>
     */
    ResponseMessage<Submission> judgeSubmission(Submission submission);
}
