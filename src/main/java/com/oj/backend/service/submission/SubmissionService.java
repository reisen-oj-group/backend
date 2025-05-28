package com.oj.backend.service.submission;

import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.pojo.submission.Submission;

/**
 * The interface Submission service.
 */
public interface SubmissionService {
    /**
     * Judge submission response message.
     *
     * @param submission the submission
     * @return the response message
     */
    ResponseMessage<Submission> judgeSubmission(Submission submission);
}
