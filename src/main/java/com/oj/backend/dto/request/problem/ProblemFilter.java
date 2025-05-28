package com.oj.backend.dto.request.problem;

import lombok.Data;

import java.util.List;

/**
 * The type Problem filter.
 */
@Data
public class ProblemFilter {
    private Integer minDifficulty;

    private Integer maxDifficulty;

    private List<Integer> tags;

    private String keywords;    // problem的ID或title

    private Integer user;       // user的ID

    private Integer page = 1;   // 页码
    private Integer pageSize = 50;  // 默认页面存储50条数据
}

//export interface ProblemFilterParams {
//    minDifficulty?: number
//    maxDifficulty?: number
//    tags?: TagId[]
//    keywords?: string
//}
