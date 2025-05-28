package com.oj.backend.dto.response.config;

import com.oj.backend.config.enums.CodeLang;
import com.oj.backend.config.enums.Difficulty;
import com.oj.backend.config.enums.UserLang;
import com.oj.backend.config.enums.Verdict;
import com.oj.backend.controller.config.ConfigController;
import com.oj.backend.pojo.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 系统配置同步响应DTO.
 * <p>用于系统启动时向客户端同步基础配置数据</p>
 *
 * @see ConfigController#syncConfig() 关联的Controller方法
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncConfigResponse {
    /**
     * 标签映射集
     * <p>Key: 标签ID, Value: 标签对象</p>
     * @see com.oj.backend.pojo.tag.Tag 标签实体类
     */
    private Map<Integer, Tag> tags;

    /**
     * 用户语言配置映射集
     * <p>Key: 语言编码, Value: 语言配置</p>
     * @see com.oj.backend.config.enums.UserLang 用户语言枚举
     */
    private Map<String, UserLang> userLangs;

    /**
     * 编程语言配置映射集
     * <p>Key: 语言标识符, Value: 语言配置</p>
     * @see com.oj.backend.config.enums.CodeLang 编程语言枚举
     */
    private Map<String, CodeLang> codeLangs;

    /**
     * 判题结果类型映射集
     * <p>Key: 结果编码, Value: 结果对象</p>
     * @see com.oj.backend.config.enums.Verdict 判题结果枚举
     */
    private Map<String, Verdict> verdicts;

    /**
     * 题目难度列表
     * @see com.oj.backend.config.enums.Difficulty 难度级别枚举
     */
    private List<Difficulty> difficulties;
}
