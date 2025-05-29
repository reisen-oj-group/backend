package com.oj.backend.dto.request.submission;

import lombok.Data;

@Data
public class SubmissionDTO {
    private Integer problem;
    private String lang;
    private String code;
}
