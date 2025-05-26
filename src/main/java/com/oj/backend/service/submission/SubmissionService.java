package com.oj.backend.service.submission;

import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.pojo.submission.Submission;

public interface SubmissionService {
    ResponseMessage<Submission> judgeSubmission(Submission submission);
}
