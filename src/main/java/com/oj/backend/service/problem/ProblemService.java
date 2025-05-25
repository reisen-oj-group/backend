package com.oj.backend.service.problem;

import com.oj.backend.dto.request.problem.ProblemIdDTO;
import com.oj.backend.dto.response.problem.ProblemResponseVO;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.dto.response.problem.ProblemListVO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.request.problem.ProblemFilter;

public interface ProblemService {
    ResponseMessage<ProblemListVO> getProblemList(ProblemFilter problemFilter);
    ResponseMessage<ProblemResponseVO> returnProblemMessage(ProblemIdDTO problemIdDTO);
    ResponseMessage<Problem> problemUpdate(Integer id, Problem problem);
}
