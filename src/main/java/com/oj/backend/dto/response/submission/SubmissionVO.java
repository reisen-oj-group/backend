package com.oj.backend.dto.response.submission;

import com.oj.backend.pojo.submission.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionVO {
    private Record record;
}
