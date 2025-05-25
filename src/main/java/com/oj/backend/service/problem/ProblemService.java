package com.oj.backend.service.problem;

import com.oj.backend.dto.problem.ProblemIdDTO;
import com.oj.backend.dto.problem.ProblemResponseDTO;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.dto.problem.ProblemListResponse;
import com.oj.backend.response.ResponseMessage;
import com.oj.backend.dto.problem.ProblemFilter;

public interface ProblemService {
    ResponseMessage<ProblemListResponse> getProblemList(ProblemFilter problemFilter);
    ResponseMessage<ProblemResponseDTO> returnProblemMessage(ProblemIdDTO problemIdDTO);
    ResponseMessage<Problem> problemUpdate(Integer id, Problem problem);
}
