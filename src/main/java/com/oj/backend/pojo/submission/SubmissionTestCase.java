package com.oj.backend.pojo.submission;

import lombok.Data;

/**
 * The type Submission test case.
 */
// Submission用类，用于存储提交的testcase，id与verdict为必须
@Data
public class SubmissionTestCase {
    private Integer id;
    private String verdict;
    private Integer time;
    private Integer memory;
    private Integer score;
    private String input;
    private String output;
    private String checker;
}
