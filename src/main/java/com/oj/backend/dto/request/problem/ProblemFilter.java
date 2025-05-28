package com.oj.backend.dto.request.problem;

import lombok.Data;

import java.util.List;

/**
 * 题目筛选条件参数类.
 *
 * <p><strong>参数说明：</strong>
 * <table border="1">
 *   <tr>
 *     <th>字段</th>
 *     <th>类型</th>
 *     <th>必填</th>
 *     <th>说明</th>
 *     <th>前端对应字段</th>
 *   </tr>
 *   <tr>
 *     <td>minDifficulty</td>
 *     <td>Integer</td>
 *     <td>否</td>
 *     <td>最小难度值（闭区间）</td>
 *     <td>minDifficulty</td>
 *   </tr>
 *   <tr>
 *     <td>maxDifficulty</td>
 *     <td>Integer</td>
 *     <td>否</td>
 *     <td>最大难度值（闭区间）</td>
 *     <td>maxDifficulty</td>
 *   </tr>
 *   <tr>
 *     <td>tags</td>
 *     <td>List&lt;Integer&gt;</td>
 *     <td>否</td>
 *     <td>标签ID列表（AND关系）</td>
 *     <td>tags</td>
 *   </tr>
 *   <tr>
 *     <td>keywords</td>
 *     <td>String</td>
 *     <td>否</td>
 *     <td>搜索关键词（匹配题目ID或标题）</td>
 *     <td>keywords</td>
 *   </tr>
 *   <tr>
 *     <td>user</td>
 *     <td>Integer</td>
 *     <td>否</td>
 *     <td>用户ID（用于筛选特定用户的题目）</td>
 *     <td>user</td>
 *   </tr>
 *   <tr>
 *     <td>page</td>
 *     <td>Integer</td>
 *     <td>是</td>
 *     <td>页码（默认值：1）</td>
 *     <td>page</td>
 *   </tr>
 *   <tr>
 *     <td>pageSize</td>
 *     <td>Integer</td>
 *     <td>否</td>
 *     <td>每页数量（默认值：50，最大值：100）</td>
 *     <td>pageSize</td>
 *   </tr>
 * </table>
 *
 * <p><strong>前端接口兼容说明：</strong>
 * 完全兼容以下TypeScript接口定义：
 * <pre>{@code
 * interface ProblemFilterParams {
 *   minDifficulty?: number
 *   maxDifficulty?: number
 *   tags?: number[] // 对应Java的List<Integer>
 *   keywords?: string
 *   page?: number
 *   user?: number
 *   // 注意：前端pageSize参数将自动映射到pageSize字段
 * }
 * }</pre>
 *
 * @see Data Lombok数据注解
 */
@Data
public class ProblemFilter {
    /**
     * 最小难度值（包含）.
     *
     * <p>与maxDifficulty共同构成闭区间查询条件
     * <p><b>示例：</b>设置minDifficulty=3时，只查询难度≥3的题目
     */
    private Integer minDifficulty;

    /**
     * 最大难度值（包含）.
     *
     * <p><b>约束条件：</b>若同时设置minDifficulty和maxDifficulty，
     * 需保证maxDifficulty ≥ minDifficulty
     */
    private Integer maxDifficulty;

    /**
     * 标签ID列表.
     *
     * <p><b>查询逻辑：</b>
     * <ul>
     *   <li>空列表/null：不按标签过滤</li>
     *   <li>非空列表：查询包含<strong>所有</strong>指定标签的题目（AND关系）</li>
     * </ul>
     */
    private List<Integer> tags;

    /**
     * 搜索关键词.
     *
     * <p><b>匹配规则：</b>
     * <ul>
     *   <li>对题目ID进行精确匹配</li>
     *   <li>对题目标题进行模糊匹配（LIKE %keyword%）</li>
     * </ul>
     */
    private String keywords;

    /**
     * 用户ID.
     *
     * <p><b>特殊用途：</b>
     * <ul>
     *   <li>管理员：可查看指定用户创建的题目</li>
     *   <li>普通用户：仅能查询自己的题目（自动填充当前用户ID）</li>
     * </ul>
     */
    private Integer user;

    /**
     * 当前页码.
     *
     * <p><b>默认值：</b>1
     * <p><b>约束：</b>≥1的整数
     */
    private Integer page = 1;

    /**
     * 每页记录数.
     *
     * <p><b>默认值：</b>50
     * <p><b>最大值：</b>100（超过时自动截断）
     */
    private Integer pageSize = 50;
}
