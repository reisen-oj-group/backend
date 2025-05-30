package com.oj.backend.service.problem;

import com.oj.backend.dto.request.problem.ProblemFilter;
import com.oj.backend.dto.request.problem.ProblemIdDTO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.response.problem.ProblemListVO;
import com.oj.backend.dto.response.problem.ProblemResponseVO;
import com.oj.backend.pojo.problem.Problem;

/**
 * 题目服务接口
 * <p>定义题目相关业务操作的规范，为前端提供题目数据访问的统一入口</p>
 *
 * <p><b>主要功能包括：</b></p>
 * <ul>
 *   <li>题目列表筛选查询</li>
 *   <li>题目详情数据获取</li>
 *   <li>题目信息更新维护</li>
 * </ul>
 */
public interface ProblemService {
    /**
     * 获取题目列表
     * <p>支持分页和多重条件筛选，返回结果包含用户提交状态</p>
     *
     * @param problemFilter 筛选条件对象，包含以下可选参数：
     * <ul>
     *   <li><b>page</b> - 当前页码（从1开始）</li>
     *   <li><b>pageSize</b> - 每页记录数</li>
     *   <li><b>minDifficulty/maxDifficulty</b> - 难度区间筛选</li>
     *   <li><b>keywords</b> - 标题关键字搜索（支持中英文模糊匹配）</li>
     *   <li><b>user</b> - 用户ID（用于关联查询提交状态）</li>
     * </ul>
     * @return 标准响应消息，数据部分包含：
     * <pre>
     * {@code
     *   "problems": [...],  // 题目实体列表
     *   "results": [...],   // 对应题目的用户提交结果
     *   "total": 100        // 符合条件的总记录数
     * }
     * </pre>
     * @see ProblemFilter 筛选参数详细定义
     * @see ProblemListVO 返回数据结构定义
     */
    ResponseMessage<ProblemListVO> getProblemList(ProblemFilter problemFilter);

    /**
     * 获取题目详情
     * <p>根据题目ID获取完整题目信息，并包含指定用户的提交记录</p>
     *
     * @param problemIdDTO 参数传输对象，包含：
     *                     <ul>
     *                       <li><b>problem</b> - 题目ID（必填）</li>
     *                       <li><b>user</b> - 用户ID（用于获取该用户的提交结果）</li>
     *                     </ul>
     * @param userId
     * @return 标准响应消息，数据部分包含：
     * <pre>
     * {@code
     *   "problem": {...},  // 题目详细实体
     *   "result": {...}    // 用户提交结果（可能为null）
     * }
     * </pre>
     * @apiNote 当user参数为null时，result字段返回null
     */
    ResponseMessage<ProblemResponseVO> returnProblemMessage(ProblemIdDTO problemIdDTO, Integer userId);

    /**
     * 更新题目信息
     * <p>根据题目ID全量更新题目数据，需包含完整题目对象</p>
     *
     * @param id      要更新的题目ID（路径参数）
     * @param problem 包含新数据的题目对象（请求体）
     * @param userId
     * @return 标准响应消息，成功时返回更新后的题目实体，失败时返回错误信息
     * <ul>
     *   <li>题目不存在（404）</li>
     *   <li>数据校验失败（400）</li>
     * </ul>
     * @implSpec 实现类应保证ID参数与题目对象内的ID一致性
     */
    ResponseMessage<Problem> problemUpdate(Integer id, Problem problem, Integer userId);
}
