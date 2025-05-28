package com.oj.backend.service.config;

import com.oj.backend.dto.response.config.SyncConfigResponse;

/**
 * 系统配置服务接口
 * <p>定义获取系统全局配置的标准方法，用于统一管理前端所需的各类静态配置数据</p>
 *
 * <p><b>典型配置包括：</b></p>
 * <ul>
 *   <li>标签体系</li>
 *   <li>语言选项</li>
 *   <li>编程语言支持</li>
 *   <li>判题结果类型</li>
 *   <li>题目难度分级</li>
 * </ul>
 *
 * @see com.oj.backend.service.config.impl.ConfigServiceImpl 默认实现类
 * @see SyncConfigResponse 返回数据结构
 */
public interface ConfigService {
    /**
     * 获取完整的系统配置数据
     * <p>该方法通常用于：</p>
     * <ol>
     *   <li>前端初始化时加载基础配置</li>
     *   <li>系统配置变更后同步最新数据</li>
     * </ol>
     *
     * @return 包含所有系统配置的响应对象，结构如下：
     * <pre>{@code
     * {
     *   "tags": Map<Integer, Tag>,
     *   "userLangs": Map<String, UserLang>,
     *   "codeLangs": Map<String, CodeLang>,
     *   "verdicts": Map<String, Verdict>,
     *   "difficulties": List<Difficulty>
     * }}
     * </pre>
     *
     * @apiNote 返回数据中的标签(tags)为动态从数据库获取，其余配置为静态预定义
     */
    
    SyncConfigResponse getConfig();
}
