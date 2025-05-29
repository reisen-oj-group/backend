package com.oj.backend.dto.request.submission;

import lombok.Data;

@Data
public class RecordFilter {
    private Integer page = 1;
    private Integer pageSize = 50;
}
