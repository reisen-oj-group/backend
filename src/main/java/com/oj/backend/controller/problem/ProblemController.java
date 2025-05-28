package com.oj.backend.controller.problem;

import com.oj.backend.dto.request.problem.ProblemIdDTO;
import com.oj.backend.dto.response.problem.ProblemResponseVO;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.dto.response.problem.ProblemListVO;
import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.service.problem.ProblemService;
import com.oj.backend.dto.request.problem.ProblemFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Problem controller.
 */
@RestController
@RequestMapping("/api/problem")
@CrossOrigin
public class ProblemController {
    /**
     * The Problem service.
     */
    @Autowired
    ProblemService problemService;

    /**
     * Return problem message response message.
     *
     * @param problemIdDTO the problem id dto
     * @return the response message
     */
    @PostMapping("")
    public ResponseMessage<ProblemResponseVO> returnProblemMessage(@RequestBody ProblemIdDTO problemIdDTO){
        return problemService.returnProblemMessage(problemIdDTO);
    }

    /**
     * Update problem response message.
     *
     * @param id      the id
     * @param problem the problem
     * @return the response message
     */
    @PostMapping("/{problemId}/edit")
    public ResponseMessage<Problem> updateProblem(
            @PathVariable("problemId")  Integer id,
            @RequestBody Problem problem
    ){
        return problemService.problemUpdate(id, problem);
    }

    /**
     * Get problem list response message.
     *
     * @param problemFilter the problem filter
     * @return the response message
     */
    @PostMapping("/list")
    public ResponseMessage<ProblemListVO> getProblemList(@RequestBody ProblemFilter problemFilter){
        return problemService.getProblemList(problemFilter);
    }
}
