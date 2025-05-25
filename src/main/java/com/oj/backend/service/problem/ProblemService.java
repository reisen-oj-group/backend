package com.oj.backend.service.problem;

import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.response.ProblemListResponse;
import com.oj.backend.response.ResponseMessage;
import com.oj.backend.utils.filter.ProblemFilter;
import org.springframework.stereotype.Service;

public interface ProblemService {
    ResponseMessage<ProblemListResponse> getProblemList(ProblemFilter problemFilter);

    ResponseMessage<Problem> problemUpdate(Integer id, Problem problem);
}
