package com.oj.backend.controller.problem;

import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.response.ProblemListResponse;
import com.oj.backend.response.ResponseMessage;
import com.oj.backend.service.problem.ProblemService;
import com.oj.backend.utils.filter.ProblemFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
public class ProblemController {
    @Autowired
    ProblemService problemService;

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
