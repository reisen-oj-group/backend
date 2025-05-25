package com.oj.backend.dto.problem;

import lombok.Data;

@Data
public class ProblemFilter {
    private Integer minDifficulty;

    private Integer maxDifficulty;

    private String keywords;    // problem的ID或title

    private Integer user;       // user的ID

    private Integer page = 1;   // 页码
    private Integer pageSize = 50;  // 默认页面存储50条数据
}
