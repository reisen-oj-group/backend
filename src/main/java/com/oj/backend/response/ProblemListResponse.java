package com.oj.backend.response;

import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.utils.result.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemListResponse {
    private List<Problem> problems;
    private List<Result> results;
    private Integer total;
}
