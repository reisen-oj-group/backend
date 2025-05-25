package com.oj.backend.pojo.submission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@TableName("submission")
public class Submission {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("problem_id")
    private Integer problemId;

    @TableField("user_id")
    private Integer userId;

    @TableField("contest_id")
    private Integer contestId;

    @TableField("submission_time")
    private LocalDateTime submissionTime;   // 提交时间

    @TableField("evaluation_time")
    private LocalDateTime evaluationTime;   // 评测时间

    private String lang;                    // 评测语言

    private String verdict;                 // 评测结果

    private Integer score;                  // 得分，主要用于部分分


    @TableField("time_used")
    private Integer timeUsed;               // 评测用时

    @TableField("memory_used")
    private Integer memoryUsed;             // 占用空间

    @TableField("code_length")
    private Integer codeLength;             // 代码长度

    private String code;

    @TableField(typeHandler = JacksonTypeHandler.class, value = "compile_info")
    private Map<String, Object> compileInfo;        // 两个键值对，success: boolean；message: string

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String, Object>> testcases;    // 测试点详细信息
    //  id: number
    //  verdict: string
    //  time?: number
    //  memory?: number
    //  score?: number
    //  input?: string
    //  output?: string
    //  checker?: string
}
