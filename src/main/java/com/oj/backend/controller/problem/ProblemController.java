package com.oj.backend.controller.problem;

import com.oj.backend.dto.problem.ProblemIdDTO;
import com.oj.backend.dto.problem.ProblemResponseDTO;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.dto.problem.ProblemListResponse;
import com.oj.backend.response.ResponseMessage;
import com.oj.backend.service.problem.ProblemService;
import com.oj.backend.dto.problem.ProblemFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problem")
public class ProblemController {
    @Autowired
    ProblemService problemService;

    @PostMapping("")
    public ResponseMessage<ProblemResponseDTO> returnProblemMessage(@RequestBody ProblemIdDTO problemIdDTO){
        return problemService.returnProblemMessage(problemIdDTO);
    }

    @PostMapping("/{problemId}/edit")
    public ResponseMessage<Problem> updateProblem(
            @PathVariable("problemId")  Integer id,
            @RequestBody Problem problem
    ){
        return problemService.problemUpdate(id, problem);
    }

    @PostMapping("/list")
    public ResponseMessage<ProblemListResponse> getProblemList(@RequestBody ProblemFilter problemFilter){
        return problemService.getProblemList(problemFilter);
    }
}
