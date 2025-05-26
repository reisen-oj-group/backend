package com.oj.backend.pojo.submission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Submission用，存储编译信息
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionCompileInfo {
    private Boolean success;
    private String message;
}
