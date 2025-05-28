package com.oj.backend.config.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示用户界面语言的配置项。
 * 定义用户可选择的界面语言选项。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLang {
    /**
     * 语言标识符（遵循ISO 639标准）
     * <p>示例值：
     * <ul>
     *   <li>"zh-CN" - 中文</li>
     *   <li>"en-US" - 英文</li>
     *   <li>"zh-TW" - 繁体中文</li>
     * </ul>
     */
    private String id;

    /**
     * 语言的本地化显示名称
     * <p>注意：
     * <ul>
     *   <li>应使用目标语言本身书写（如中文显示"简体中文"）</li>
     *   <li>需与前端语言选择器中的显示一致</li>
     * </ul>
     * <p>示例值：
     * <ul>
     *   <li>"简体中文"</li>
     *   <li>"English"</li>
     *   <li>"繁体中文"</li>
     * </ul>
     */
    private String description;
}
