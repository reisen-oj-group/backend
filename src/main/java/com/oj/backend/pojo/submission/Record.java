package com.oj.backend.pojo.submission;

import com.oj.backend.dto.response.user.UserResponseVO;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.pojo.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Record {
    private Long id;
    private LocalDateTime submission;
    private LocalDateTime evaluation;
    private String lang;
    private String verdict;
    private Integer score;
    private Integer length;
    private String code;
    private SubmissionCompileInfo compile;
    private Detail detail;
    private Problem problem;
    private UserResponseVO user;

    public Record(Submission submission, User user, Problem problem){
        this.id = submission.getId();
        this.submission = submission.getSubmissionTime();
        this.evaluation = submission.getEvaluationTime();
        this.lang = submission.getLang();
        this.verdict = submission.getVerdict();
        this.score = submission.getScore();
        this.length = submission.getCodeLength();
        this.code = submission.getCode();
        this.compile = submission.getCompileInfo();
        this.user = new UserResponseVO(user);
        this.problem = problem;
    }
}
