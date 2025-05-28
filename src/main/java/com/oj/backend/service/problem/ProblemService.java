package com.oj.backend.service.problem;

import com.oj.backend.dto.request.problem.ProblemIdDTO;
import com.oj.backend.dto.response.problem.ProblemResponseVO;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.dto.response.problem.ProblemListVO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.dto.request.problem.ProblemFilter;

/**
 * The interface Problem service.
 */
public interface ProblemService {
    /**
     * Gets problem list.
     *
     * @param problemFilter the problem filter
     * @return the problem list
     */
    ResponseMessage<ProblemListVO> getProblemList(ProblemFilter problemFilter);

    /**
     * Return problem message response message.
     *
     * @param problemIdDTO the problem id dto
     * @return the response message
     */
    ResponseMessage<ProblemResponseVO> returnProblemMessage(ProblemIdDTO problemIdDTO);

    /**
     * Problem update response message.
     *
     * @param id      the id
     * @param problem the problem
     * @return the response message
     */
    ResponseMessage<Problem> problemUpdate(Integer id, Problem problem);
}
