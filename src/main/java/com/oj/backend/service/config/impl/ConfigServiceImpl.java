package com.oj.backend.service.config.impl;

import com.oj.backend.config.enums.CodeLang;
import com.oj.backend.config.enums.Difficulty;
import com.oj.backend.config.enums.UserLang;
import com.oj.backend.config.enums.Verdict;
import com.oj.backend.dto.response.config.SyncConfigResponse;
import com.oj.backend.mapper.tag.TagMapper;
import com.oj.backend.pojo.tag.Tag;
import com.oj.backend.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统配置服务实现类
 * <p>提供系统基础配置数据的集中管理，包括标签、语言、评测结果等静态配置</p>
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    /**
     * 标签数据访问器
     * <p>用于从数据库获取动态标签数据</p>
     */
    @Autowired
    TagMapper tagMapper;

    /**
     * 固定用户语言配置表
     * <p>系统支持的界面语言列表，包含语言代码和显示名称</p>
     *
     * <table border="1">
     *   <caption>预置语言配置</caption>
     *   <tr><th>语言代码</th><th>语言名称</th></tr>
     *   <tr><td>en-US</td><td>English</td></tr>
     *   <tr><td>zh-CN</td><td>简体中文</td></tr>
     *   <tr><td>zh-TW</td><td>繁体中文</td></tr>
     * </table>
     */
    public static final Map<String, UserLang> FIXED_USER_LANGS = Map.of(
            "en-US", new UserLang("en-US", "English"),
            "zh-CN", new UserLang("zh-CN", "简体中文"),
            "zh-TW", new UserLang("zh-TW", "繁体中文")
    );

    /**
     * 固定编程语言配置表
     * <p>系统支持的编程语言列表，包含编译器标识和显示名称</p>
     *
     * <table border="1">
     *   <caption>预置编程语言</caption>
     *   <tr><th>标识符</th><th>语言名称</th><th>时限倍率</th></tr>
     *   <tr><td>cpp</td><td>C++17 (GCC 9)</td><td>1</td></tr>
     *   <tr><td>java</td><td>Java 11</td><td>2</td></tr>
     *   <tr><td>python</td><td>Python 3.8</td><td>3</td></tr>
     * </table>
     */
    public static final Map<String, CodeLang> FIXED_CODE_LANGS = Map.of(
            "cpp", new CodeLang("cpp", "C++17 (GCC 9)", 1),
            "java", new CodeLang("java", "Java 11", 2),
            "python", new CodeLang("python", "Python 3.8", 3)
    );

    /**
     * 固定评测结果配置表
     * <p>系统定义的判题结果类型，包含状态码和显示样式</p>
     *
     * <table border="1">
     *   <caption>判题结果类型</caption>
     *   <tr><th>代码</th><th>全称</th><th>简写</th><th>显示颜色</th></tr>
     *   <tr><td>AC</td><td>Accepted</td><td>AC</td><td>#67C23A</td></tr>
     *   <tr><td>WA</td><td>Wrong Answer</td><td>WA</td><td>#F56C6C</td></tr>
     *   <tr><td>RE</td><td>Runtime Error</td><td>RE</td><td>#6A3BC0</td></tr>
     *   <tr><td>TLE</td><td>Time Limit Exceeded</td><td>TLE</td><td>#E6A23C</td></tr>
     *   <tr><td>MLE</td><td>Memory Limit Exceeded</td><td>MLE</td><td>#E6A23C</td></tr>
     *   <tr><td>CE</td><td>Compile Error</td><td>CE</td><td>#909399</td></tr>
     *   <tr><td>UKE</td><td>Unknown Error</td><td>UKE</td><td>#909399</td></tr>
     * </table>
     */
    public static final Map<String, Verdict> FIXED_VERDICTS = Map.of(
            "AC", new Verdict("AC", "Accepted", "AC", "#67C23A"),
            "WA", new Verdict("WA", "Wrong Answer", "WA", "#F56C6C"),
            "RE", new Verdict("RE", "Runtime Error", "RE", "#6A3BC0"),
            "TLE", new Verdict("TLE", "Time Limit Exceeded", "TLE", "#E6A23C"),
            "MLE", new Verdict("MLE", "Memory Limit Exceeded", "MLE", "#E6A23C"),
            "CE", new Verdict("CE", "Compile Error", "CE", "#909399"),
            "UKE", new Verdict("UKE", "Unknown Error", "UKE", "#909399")
    );

    /**
     * 固定难度等级配置
     * <p>题目难度分级标准，按分数区间划分</p>
     *
     * <table border="1">
     *   <caption>难度等级划分</caption>
     *   <tr><th>最低分</th><th>最高分</th><th>等级名称</th></tr>
     *   <tr><td>800</td><td>1099</td><td>入门</td></tr>
     *   <tr><td>1100</td><td>1399</td><td>简单</td></tr>
     *   <tr><td>1400</td><td>1699</td><td>中等</td></tr>
     *   <tr><td>1700</td><td>1999</td><td>较难</td></tr>
     *   <tr><td>2000</td><td>2299</td><td>困难</td></tr>
     *   <tr><td>2300</td><td>2599</td><td>挑战</td></tr>
     *   <tr><td>2600</td><td>2899</td><td>精英</td></tr>
     *   <tr><td>2900</td><td>3199</td><td>专家</td></tr>
     *   <tr><td>3200</td><td>3500</td><td>大师</td></tr>
     * </table>
     */
    public static final List<Difficulty> FIXED_DIFFICULTIES = List.of(
            new Difficulty(800, 1099, "入门"),
            new Difficulty(1100, 1399, "简单"),
            new Difficulty(1400, 1699, "中等"),
            new Difficulty(1700, 1999, "较难"),
            new Difficulty(2000, 2299, "困难"),
            new Difficulty(2300, 2599, "挑战"),
            new Difficulty(2600, 2899, "精英"),
            new Difficulty(2900, 3199, "专家"),
            new Difficulty(3200, 3500, "大师")
    );

    /**
     * 获取完整系统配置
     * @return 包含所有配置数据的响应对象
     * @see SyncConfigResponse
     */
    public SyncConfigResponse getConfig() {
        return new SyncConfigResponse(
                getTags(),
                FIXED_USER_LANGS,
                FIXED_CODE_LANGS,
                FIXED_VERDICTS,
                FIXED_DIFFICULTIES
        );
    }

    /**
     * 获取标签映射表
     * @return 以标签ID为键的标签映射表
     */
    private Map<Integer, Tag> getTags() {
        return tagMapper.selectList(null)
                .stream()
                .collect(Collectors.toMap(Tag::getId, Function.identity()));
    }
}
