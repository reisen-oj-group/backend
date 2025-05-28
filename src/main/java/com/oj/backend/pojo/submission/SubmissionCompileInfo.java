package com.oj.backend.pojo.submission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提交记录的编译信息
 * <p>用于存储代码编译阶段的结果</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionCompileInfo {
    /**
     * 编译是否成功
     */
    private Boolean success;

    /**
     * 编译信息
     * <p>成功时为编译器警告信息，失败时为错误日志</p>
     * <p><b>内容可能包含：</b></p>
     * <ul>
     *   <li>错误行号及描述</li>
     *   <li>编译器版本信息</li>
     *   <li>标准库引用问题</li>
     * </ul>
     */
    private String message;
}
