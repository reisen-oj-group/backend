package com.oj.backend.controller.submission;

import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.pojo.submission.Submission;
import com.oj.backend.service.submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Submission controller.
 */
@RestController
// 测试用，前端接口写好后要修改
@RequestMapping("/api/submission")
@CrossOrigin
public class SubmissionController {
    /**
     * The Submission service.
     */
    @Autowired
    SubmissionService submissionService;

    /**
     * Judge submission response message.
     *
     * @param submission the submission
     * @return the response message
     */
    @PostMapping("")
    public ResponseMessage<Submission> judgeSubmission(@RequestBody Submission submission){
        return submissionService.judgeSubmission(submission);
    }
}
