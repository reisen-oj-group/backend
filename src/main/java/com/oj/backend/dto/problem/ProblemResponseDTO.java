package com.oj.backend.dto.problem;

import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.utils.result.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemResponseDTO {
    private Problem problem;
    private Result result;
}
