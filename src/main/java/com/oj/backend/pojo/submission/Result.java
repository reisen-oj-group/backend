package com.oj.backend.pojo.submission;

import com.oj.backend.pojo.submission.Submission;
import lombok.Data;

@Data
public class Result {
    private Integer problemId;

    private Integer contestId;  // 可选

    private Integer userId;

    private String judge;

    private Integer attempt;    // 尝试次数，仅在 ACM 比赛有效
    private Integer penalty;    // 最终罚时，仅在 ACM 比赛有效

    private Integer time;       // 程序用时（取 AC 提交最短用时）

    public Result(Submission submission) {
        this.problemId = submission.getProblemId();
        this.contestId = submission.getContestId();
        this.userId = submission.getUserId();
        this.time = submission.getTimeUsed();
        this.judge = convertToJudge(submission.getVerdict(), submission.getScore());
    }

    private String convertToJudge(String verdict, Integer score) {
        if (verdict.equals("AC")) {
            return "correct";
        } else if (score != null && score > 0) {
            return String.valueOf(score);
        } else {
            return "incorrect";
        }
    }
}
