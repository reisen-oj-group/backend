package com.oj.backend.service.problem;

import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.dto.problem.ProblemListResponse;
import com.oj.backend.response.ResponseMessage;
import com.oj.backend.dto.problem.ProblemFilter;

public interface ProblemService {
    ResponseMessage<ProblemListResponse> getProblemList(ProblemFilter problemFilter);

    ResponseMessage<Problem> problemUpdate(Integer id, Problem problem);
}
