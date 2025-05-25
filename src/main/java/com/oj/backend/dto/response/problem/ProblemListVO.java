package com.oj.backend.dto.response.problem;

import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.pojo.submission.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemListVO {
    private List<Problem> problems;
    private List<Result> results;
    private Integer total;
}
