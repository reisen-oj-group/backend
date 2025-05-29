package com.oj.backend.pojo.submission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oj.backend.dto.request.submission.SubmissionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 提交记录实体类
 * <p>对应数据库 submission 表，存储用户提交的代码及评测结果</p>
 *
 * <p><b>特殊处理：</b>autoResultMap=true 用于处理复杂类型字段的自动转换</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "submission", autoResultMap = true)
public class Submission {
    /** 自增主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 题目ID */
    @JsonProperty("problem_id")
    @TableField("problem_id")
    private Integer problemId;

    /** 用户ID */
    @JsonProperty("user_id")
    @TableField("user_id")
    private Integer userId;

    /**
     * 比赛ID
     * <p>普通提交时为null</p>
     */
    @JsonProperty("contest_id")
    @TableField("contest_id")
    private Integer contestId;

    /** 提交时间 */
    @JsonProperty("submission_time")
    @TableField("submission_time")
    private LocalDateTime submissionTime;

    /**
     * 评测完成时间
     * <p>未完成评测时为null</p>
     */
    @JsonProperty("evaluation_time")
    @TableField("evaluation_time")
    private LocalDateTime evaluationTime;

    /**
     * 编程语言
     * <p>示例值：</p>
     * <ul>
     *   <li>"C++17 (GCC9)"</li>
     *   <li>"Java 11"</li>
     *   <li>"Python 3.8"</li>
     * </ul>
     */
    private String lang;

    /**
     * 评测结果缩写
     * @see <a href="#verdict-取值说明">verdict 取值说明</a>
     */
    private String verdict;

    /**
     * 得分（百分制）
     * <p>部分分制题目有效，AC时通常为100</p>
     */
    private Integer score;

    /** 程序运行用时(ms) */
    @JsonProperty("time_used")
    @TableField("time_used")
    private Integer timeUsed;

    /** 内存占用(KB) */
    @JsonProperty("memory_used")
    @TableField("memory_used")
    private Integer memoryUsed;

    /** 源代码内容 */
    private String code;

    /** 代码长度（字符数） */
    @JsonProperty("code_length")
    @TableField("code_length")
    private Integer codeLength;

    /**
     * 编译信息
     * @see SubmissionCompileInfo
     */
    @JsonProperty("compile_info")
    @TableField(typeHandler = JacksonTypeHandler.class, value = "compile_info")
    private SubmissionCompileInfo compileInfo;

    /**
     * 测试点详情列表
     * @see SubmissionTestCase
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<SubmissionTestCase> testcases;

    /**
     * 设置代码并自动计算长度
     * <p>会同步更新codeLength字段</p>
     *
     * @param code 用户源代码
     */
    public void setCode(String code) {
        this.code = code;
        this.codeLength = (code != null) ? code.length() : 0;
    }

//    public Submission(SubmissionDTO submissionDTO){
//        this.code = submissionDTO.getCode();
//        this.lang = submissionDTO.getLang();
//        this.problemId = submissionDTO.getProblem();
//    }

    // 删除原构造函数，改为静态工厂方法，原来的方法会覆盖掉无参构造，当查询数据库时，MyBatis 必须依赖无参构造来反射创建对象，通过反射注入字段值，返回组装好的对象
    public static Submission fromDTO(SubmissionDTO dto) {
        return Submission.builder()
                .code(dto.getCode())
                .lang(dto.getLang())
                .problemId(dto.getProblem())
                .build();
    }
}
