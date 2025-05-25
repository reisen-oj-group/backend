package com.oj.backend.service.config.impl;

import com.oj.backend.config.CodeLang;
import com.oj.backend.config.Difficulty;
import com.oj.backend.config.UserLang;
import com.oj.backend.config.Verdict;
import com.oj.backend.mapper.tag.TagMapper;
import com.oj.backend.pojo.tag.Tag;
import com.oj.backend.response.ResponseMessage;
import com.oj.backend.response.SyncConfigResponse;
import com.oj.backend.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    TagMapper tagMapper;

    // 固定用户语言配置
    private static final Map<String, UserLang> FIXED_USER_LANGS = Map.of(
            "en-US", new UserLang("en-US", "English"),
            "zh-CN", new UserLang("zh-CN", "简体中文"),
            "zh-TW", new UserLang("zh-TW", "繁体中文")
    );

    // 固定编程语言配置
    private static final Map<String, CodeLang> FIXED_CODE_LANGS = Map.of(
            "en-US", new CodeLang("cpp", "C++17 (GCC 9)", 1),
            "zh-CN", new CodeLang("java", "Java 11", 2),
            "zh-TW", new CodeLang("python", "Python 3.8", 3)
    );

    // 固定判题结果配置
    private static final Map<String, Verdict> FIXED_VERDICTS = Map.of(
            "AC", new Verdict("AC", "Accepted", "AC", "#67C23A"),
            "WA", new Verdict("WA", "Wrong Answer", "WA", "#F56C6C"),
            "RE", new Verdict("RE", "Runtime Error", "RE", "#6A3BC0"),
            "TLE", new Verdict("TLE", "Time Limit Exceeded", "TLE", "#E6A23C"),
            "MLE", new Verdict("MLE", "Memory Limit Exceeded", "MLE", "#E6A23C"),
            "CE", new Verdict("CE", "Compile Error", "CE", "#909399"),
            "UKE", new Verdict("UKE", "Unknown Error", "UKE", "#909399")
    );

    // 固定难度等级配置
    private static final List<Difficulty> FIXED_DIFFICULTIES = List.of(
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

    public SyncConfigResponse getConfig() {
        return new SyncConfigResponse(
                getTags(),
                FIXED_USER_LANGS,
                FIXED_CODE_LANGS,
                FIXED_VERDICTS,
                FIXED_DIFFICULTIES
        );
    }

    private Map<Integer, Tag> getTags() {
        return tagMapper.selectList(null)
                .stream()
                .collect(Collectors.toMap(Tag::getId, Function.identity()));
    }
}
