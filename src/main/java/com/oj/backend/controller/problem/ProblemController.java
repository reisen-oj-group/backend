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

@RestController
@RequestMapping("/api/problem")
@CrossOrigin
public class ProblemController {
    @Autowired
    ProblemService problemService;

    @PostMapping("")
    public ResponseMessage<ProblemResponseVO> returnProblemMessage(@RequestBody ProblemIdDTO problemIdDTO){
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
    public ResponseMessage<ProblemListVO> getProblemList(@RequestBody ProblemFilter problemFilter){
        return problemService.getProblemList(problemFilter);
    }
}
