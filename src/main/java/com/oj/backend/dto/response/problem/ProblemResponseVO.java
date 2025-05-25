package com.oj.backend.dto.response.problem;

import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.pojo.submission.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemResponseVO {
    private Problem problem;
    private Result result;
}
