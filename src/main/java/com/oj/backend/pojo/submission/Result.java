package com.oj.backend.pojo.submission;

import lombok.Data;

/**
 * 评测结果封装类
 * <p>用于比赛排名、用户数据统计等场景</p>
 */
@Data
public class Result {
    /**
     * 题目ID（必填）
     */
    private Integer problemId;

    /**
     * 比赛ID（可选）
     * <p>非比赛提交时为null</p>
     */
    private Integer contestId;

    /**
     * 用户ID（必填）
     */
    private Integer userId;

    /**
     * 判定结果
     *
     * @value "correct" | "incorrect" | 分数字符串
     * @see #convertToJudge(String, Integer) 生成逻辑
     */
    private String judge;

    /**
     * ACM赛制尝试次数（可选）
     * <p><b>仅ACM比赛有效</b></p>
     */
    private Integer attempt;

    /**
     * ACM赛制罚时（可选）
     * <p><b>仅ACM比赛有效</b></p>
     */
    private Integer penalty;

    /**
     * 程序最短用时(ms)
     * <p>仅记录AC提交的最短时间</p>
     */
    private Integer time;

    /**
     * 通过Submission构造Result
     * <p><b>注意：</b>会自动转换verdict为judge字段</p>
     *
     * @param submission 原始提交记录
     * @see #convertToJudge(String, Integer) 判定逻辑
     */
    public Result(Submission submission) {
        this.problemId = submission.getProblemId();
        this.contestId = submission.getContestId();
        this.userId = submission.getUserId();
        this.time = submission.getTimeUsed();
        this.judge = convertToJudge(submission.getVerdict(), submission.getScore());
    }

    /**
     * 原始判题结果转换
     * <p>转换规则：</p>
     * <ul>
     *   <li>AC -> "correct"</li>
     *   <li>非AC但有分 -> 分数字符串</li>
     *   <li>其他 -> "incorrect"</li>
     * </ul>
     */
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
