package com.oj.backend.config.enums;

import com.oj.backend.pojo.submission.Submission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示判题结果的判定类型。
 * 用于描述代码提交的评测结果状态（如通过/错误类型），包含前端展示所需的元信息。
 *
 * <p>通常与 {@link Submission} 关联，在下列场景使用：
 * <ul>
 *   <li>判题服务返回结果状态码</li>
 *   <li>用户提交历史的状态展示</li>
 *   <li>管理端的问题统计报表</li>
 * </ul>
 *
 * <p>系统预定义的判定类型常量（颜色使用 HEX 格式）：
 * <ul>
 *   <li>{@code AC} - Accepted (绿色 #67C23A)</li>
 *   <li>{@code WA} - Wrong Answer (红色 #F56C6C)</li>
 *   <li>{@code RE} - Runtime Error (紫色 #6A3BC0)</li>
 *   <li>{@code TLE} - Time Limit Exceeded (橙色 #E6A23C)</li>
 *   <li>{@code MLE} - Memory Limit Exceeded (橙色 #E6A23C)</li>
 *   <li>{@code CE} - Compile Error (灰色 #909399)</li>
 *   <li>{@code UKE} - Unknown Error (灰色 #909399)</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Verdict {
    /**
     * 判定类型唯一标识（全大写字母）
     * <p>建议始终使用预定义常量，禁止随意新建类型
     * <p>示例值："AC"、"WA"、"TLE"
     */
    private String id;

    /**
     * 完整状态描述（首字母大写）
     * <p>需与前端展示文本严格一致
     * <p>示例值："Wrong Answer"、"Time Limit Exceeded"
     */
    private String description;

    /**
     * 状态缩写（全大写）
     * <p>用于表格等紧凑空间展示
     * <p>示例值："WA"、"TLE"
     */
    private String abbr;

    /**
     * 状态对应的主题色（HEX格式）
     * <p>颜色使用规范：
     * <ul>
     *   <li>成功状态：绿色系</li>
     *   <li>错误状态：红色系</li>
     *   <li>资源限制：橙色系</li>
     *   <li>系统错误：灰色系</li>
     * </ul>
     * <p>示例值："#F56C6C"、"#67C23A"
     */
    private String color;
}
