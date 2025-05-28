package com.oj.backend.dto.request.problem;

import lombok.Data;

/**
 * 题目标识数据传输对象.
 *
 * <p><strong>使用场景：</strong>
 * <ul>
 *   <li>题目详情查询</li>
 *   <li>用户题目关联操作（如收藏、提交记录）</li>
 *   <li>需要同时验证题目和用户权限的接口</li>
 * </ul>
 *
 * <p><strong>字段说明：</strong>
 * <table border="1">
 *   <tr>
 *     <th width="20%">字段名</th>
 *     <th width="15%">类型</th>
 *     <th width="15%">必填</th>
 *     <th width="50%">说明</th>
 *   </tr>
 *   <tr>
 *     <td>problem</td>
 *     <td>Integer</td>
 *     <td>是</td>
 *     <td>题目唯一标识符，对应数据库主键ID</td>
 *   </tr>
 *   <tr>
 *     <td>user</td>
 *     <td>Integer</td>
 *     <td>否</td>
 *     <td>
 *       用户唯一标识符，可选场景：<br>
 *       1. 查询用户特定题目的提交记录<br>
 *       2. 验证用户对题目的操作权限<br>
 *       3. 管理员查看指定用户数据时使用
 *     </td>
 *   </tr>
 * </table>
 *
 * <p><strong>数据校验规则：</strong>
 * <ul>
 *   <li>problem字段必须为正整数（≥1）</li>
 *   <li>user字段当存在时必须为正整数（≥1）</li>
 * </ul>
 *
 * @see Data Lombok注解（自动生成getter/setter等方法）
 */
@Data
public class ProblemIdDTO {
    /**
     * 题目唯一ID.
     *
     * <p><b>约束条件：</b>
     * <ul>
     *   <li>必须对应已存在的题目</li>
     * </ul>
     *
     * <p><b>示例值：</b>12345
     */
    private Integer problem;

    /**
     * 用户唯一ID（可选）.
     */
    private Integer user;
}
