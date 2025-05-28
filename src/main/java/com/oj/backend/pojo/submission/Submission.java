package com.oj.backend.pojo.submission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The type Submission.
 */
// autoResultMap = true进行自动转换
@Data
@TableName(value = "submission", autoResultMap = true)
public class Submission {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @JsonProperty("problem_id")
    @TableField("problem_id")
    private Integer problemId;

    @JsonProperty("user_id")
    @TableField("user_id")
    private Integer userId;

    @JsonProperty("contest_id")
    @TableField("contest_id")
    private Integer contestId;

    @JsonProperty("submission_time")
    @TableField("submission_time")
    private LocalDateTime submissionTime;   // 提交时间

    @JsonProperty("evaluation_time")
    @TableField("evaluation_time")
    private LocalDateTime evaluationTime;   // 评测时间

    private String lang;                    // 评测语言,C++17 (GCC9) Java 11 Python 3.8

    private String verdict;                 // 评测结果，使用缩写
//    AC: { id: 'AC', description: 'Accepted', abbr: 'AC', color: '#67C23A' },
//    WA: { id: 'WA', description: 'Wrong Answer', abbr: 'WA', color: '#F56C6C' },
//    RE: { id: 'RE', description: 'Runtime Error', abbr: 'RE', color: '#6A3BC0' },
//    TLE: { id: 'TLE', description: 'Time Limit Exceeded', abbr: 'TLE', color: '#E6A23C' },
//    MLE: { id: 'MLE', description: 'Memory Limit Exceeded', abbr: 'MLE', color: '#E6A23C' },
//    CE: { id: 'CE', description: 'Compile Error', abbr: 'CE', color: '#909399' },
//    UKE: { id: 'UKE', description: 'Unknown Error', abbr: 'UKE', color: '#909399' },

    private Integer score;                  // 得分，主要用于部分分

    @JsonProperty("time_used")              // JsonProperty用于绑定前端json对应键值
    @TableField("time_used")
    private Integer timeUsed;               // 评测用时

    @JsonProperty("memory_used")
    @TableField("memory_used")
    private Integer memoryUsed;             // 占用空间

    private String code;

    @JsonProperty("code_length")
    @TableField("code_length")
    private Integer codeLength;             // 代码长度

    @JsonProperty("compile_info")
    @TableField(typeHandler = JacksonTypeHandler.class, value = "compile_info")
    private SubmissionCompileInfo compileInfo;        // 两个键值对，success: boolean；message: string

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<SubmissionTestCase> testcases;    // 测试点详细信息
    //  id: number
    //  verdict: string
    //  time?: number
    //  memory?: number
    //  score?: number
    //  input?: string
    //  output?: string
    //  checker?: string

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
        this.codeLength = (code != null) ? code.length() : 0;
    }
}
