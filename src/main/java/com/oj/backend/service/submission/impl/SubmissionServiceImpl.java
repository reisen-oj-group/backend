package com.oj.backend.service.submission.impl;

import com.oj.backend.dto.response.common.ResponseMessage;
import com.oj.backend.mapper.problem.ProblemMapper;
import com.oj.backend.mapper.submission.SubmissionMapper;
import com.oj.backend.pojo.problem.Example;
import com.oj.backend.pojo.problem.Problem;
import com.oj.backend.pojo.submission.Submission;
import com.oj.backend.pojo.submission.SubmissionCompileInfo;
import com.oj.backend.pojo.submission.SubmissionTestCase;
import com.oj.backend.service.submission.SubmissionService;
import com.oj.backend.utils.docker.DockerJudgeRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 提交评测服务实现类
 * <p>
 * 负责处理用户代码提交的完整评测流程，包括代码编译、测试用例执行和结果判定。
 * 通过Docker容器确保评测环境隔离，支持多种编程语言的评测。
 * </p>
 */
@Service
public class SubmissionServiceImpl implements SubmissionService {
    /**
     * 题目数据访问接口
     */
    @Autowired
    ProblemMapper problemMapper;

    /**
     * 提交记录数据访问接口
     */
    @Autowired
    SubmissionMapper submissionMapper;

    /**
     * 评测用户提交的代码
     *
     * @param submission 提交实体对象，包含：
     *                   - problemId: 题目ID
     *                   - code: 用户提交的源代码
     *                   - lang: 编程语言类型
     * @return 包含评测结果的响应消息：
     *         - 成功时返回完整评测结果
     *         - 失败时返回错误信息
     */
    @Override
    public ResponseMessage<Submission> judgeSubmission(Submission submission) {
        // 设置提交时间
        submission.setSubmissionTime(LocalDateTime.now());

        try {
            // 1.首先获取题目和提交的代码以及测试用例我们默认使用en-US
            Problem problem = problemMapper.selectById(submission.getProblemId());
            String code = submission.getCode();
            List<Example> enExamples = problem.getStatements().get("en-US").getExamples();
            // List<Example> zhExamples = problem.getStatements().get("zh-CN").getExamples();

            // 2.解析测试用例
            if (enExamples == null) { // 没有测试用例，设置为unknown error
                submission.setVerdict("UKE");
                submissionMapper.updateById(submission);
                return ResponseMessage.error("测试用例为空");
            }

            // 3.创建临时文件夹存储代码
            Path tmpDir = Path.of(
                    System.getProperty("java.io.tmpdir"),
                    String.format(
                            "judge_problem_%d_submission_%d",
                            submission.getProblemId(),
                            submission.getId()
                    )
            );

            // 4.保存代码至临时文件夹并返回代码路径
            Path codePath = saveCode(code, tmpDir, submission.getLang());

            if (!compileCode(submission, codePath)) {   // submission的verdict和compile_info信息已在函数内设置完成
                submissionMapper.updateById(submission);
                return ResponseMessage.error(submission.getCompileInfo().getMessage());
            }

            // 5.运行测试用例
            runTestCases(submission, problem, tmpDir, enExamples);
            submission.setEvaluationTime(LocalDateTime.now());

            // 6.更新problem的字段
            problem.setCountTotal(problem.getCountTotal() + 1);
            if (submission.getVerdict().equals("AC")) {
                problem.setCountCorrect(problem.getCountCorrect() + 1);
            }

            problemMapper.updateById(problem);
            submissionMapper.updateById(submission);

            return ResponseMessage.success(submission);

        } catch (IOException e) {
            submission.setVerdict("UKE");
            submissionMapper.updateById(submission);
            return ResponseMessage.error("错误");
        }
    }

    /**
     * 执行所有测试用例并收集结果
     *
     * @param submission 当前提交记录
     * @param problem    对应的题目对象
     * @param tmpDir     临时工作目录
     * @param examples   测试用例列表
     */
    private void runTestCases(Submission submission, Problem problem, Path tmpDir, List<Example> examples) {
        // 1.新建一个SubmissionTestCase对象列表，在测试样例中最大用时，最大内存使用，所有测试样例结束后最终的verdict ，存储到submission的字段中
        List<SubmissionTestCase> testCaseList = new ArrayList<>();
        String finalVerdict = "AC";
        int maxTimeUsed = 0, maxMemoryUsed = 0;

        for (int i = 0; i < examples.size(); i++) {
            Example example = examples.get(i);
            SubmissionTestCase testCase = runSingleTestCase(
                    i + 1,                      // 样例ID
                    tmpDir,                     // 代码路径
                    submission.getLang(),       // 使用语言
                    example.getDataI(),         // 样例输入
                    example.getDataO(),         // 样例输出
                    problem.getLimitTime(),     // 时间限制
                    problem.getLimitMemory()    // 内存限制
            );

            // 将测试用例加入列表
            testCaseList.add(testCase);

            // 更新time和memory
            maxTimeUsed = Math.max(maxTimeUsed, testCase.getTime() != null ? testCase.getTime() : 0);
            maxMemoryUsed = Math.max(maxMemoryUsed, testCase.getTime() != null ? testCase.getTime() : 0);

            if (!testCase.getVerdict().equals("AC")) {
                finalVerdict = testCase.getVerdict();
            }
        }

        // 设置运行后结果
        submission.setTestcases(testCaseList);
        submission.setTimeUsed(maxTimeUsed);
        submission.setMemoryUsed(maxMemoryUsed);
        submission.setVerdict(finalVerdict);
    }

    /**
     * 执行单个测试用例
     *
     * @param id          测试用例ID
     * @param tmpDir      临时工作目录
     * @param lang        编程语言类型
     * @param dataI       测试输入
     * @param dataO       期望输出
     * @param limitTime   时间限制(ms)
     * @param limitMemory 内存限制(KB)
     * @return 包含测试结果的SubmissionTestCase对象
     */
    private SubmissionTestCase runSingleTestCase(int id,                // 样例ID
                                                 Path tmpDir,           // 代码路径
                                                 String lang,           // 使用语言
                                                 String dataI,          // 样例输入
                                                 String dataO,          // 样例输出
                                                 Integer limitTime,     // 时间限制
                                                 Integer limitMemory) { // 内存限制
        // 用于返回，进行初始化
        SubmissionTestCase testCase = new SubmissionTestCase();
        testCase.setId(id);
        testCase.setInput(dataI);

        // 使用自定义的docker工具类运行并使用测试用例
        DockerJudgeRunner.DockerRunResult result = DockerJudgeRunner.runContainer(
                tmpDir,
                lang,
                dataI,
                limitTime,
                limitMemory
        );

        // 更新运行结果
        testCase.setOutput(result.getDataO());
        testCase.setTime(result.getTimeUsed());
        testCase.setMemory(result.getMemoryUsed());

        if (result.getVerdict().equals("TLE")) {    // 运行超过时间限制
            testCase.setVerdict("TLE");
        } else if (result.getExitCode() != 0) {     // 程序runtime error
            testCase.setVerdict("RE");
            testCase.setOutput(result.getError());
        } else {    // 查看程序输出与样例输出是否一致，normalizeOutput去除首尾空白字符，统一换行符为 \n，合并连续空白字符为单个空格
            testCase.setVerdict(
                    normalizeOutput(result.getDataO()).equals(normalizeOutput(dataO)) ? "AC" : "WA"
            );
        }

        return testCase;
    }

    /**
     * 标准化输出字符串
     *
     * @param dataO 原始输出字符串
     * @return 标准化后的字符串（去除首尾空白，统一换行符，合并连续空格）
     */
    private String normalizeOutput(String dataO) {
        return dataO.trim().replaceAll("\\r\\n", "\n").replaceAll("\\s+", " ");
    }

    /**
     * 编译用户代码
     *
     * @param submission 提交记录
     * @param codePath   代码文件路径
     * @return 编译是否成功
     */
    private boolean compileCode(Submission submission, Path codePath) {
        String codeLang = submission.getLang();
        return codeLang.equals("Java 11") || codeLang.equals("java")
                ? compileJavaCode(submission, codePath)
                : codeLang.equals("C++17 (GCC9)") || codeLang.equals("cpp")
                ? compileCppCode(submission, codePath)
                : codeLang.equals("Python 3.8") || codeLang.equals("python")
                ? true// compilePythonCode(submission, codePath)
                : false;
    }

    /**
     * 通用代码编译方法
     *
     * @param submission           提交记录
     * @param codePath             代码文件路径
     * @param processBuilderCreator 创建编译命令的工厂方法
     * @return 编译是否成功
     */
    private boolean compileCode(Submission submission,
                                Path codePath,
                                Function<Path, ProcessBuilder> processBuilderCreator) {
        try {
            // 创建 ProcessBuilder
            ProcessBuilder processBuilder = processBuilderCreator.apply(codePath);  // 动态生成各语言对应的ProcessBuilder

            // 将错误流和标准输出合并
            processBuilder.redirectErrorStream(true);

            // 执行编译
            Process process = processBuilder.start();

            // 启动进程并等待
            int exitCode = process.waitFor();

            // 处理成功/失败结果
            if (exitCode == 0) {
                submission.setCompileInfo(new SubmissionCompileInfo(true, "编译成功"));
                return true;
            } else {
                submission.setCompileInfo(new SubmissionCompileInfo(false, "编译失败"));
                return false;
            }

        }   // 异常捕获
        catch (IOException | InterruptedException e) {    // 多异常捕获
            e.printStackTrace();
            submission.setCompileInfo(new SubmissionCompileInfo(false, "编译失败"));
            return false;
        }

    }

    /**
     * 编译C++代码
     *
     * @param submission 提交记录
     * @param codePath   代码文件路径
     * @return 编译是否成功
     */
    private boolean compileCppCode(Submission submission, Path codePath) {
        // isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // 配置使用g++编译,使用docker确保跨平台编译不会出问题
        return compileCode(
                submission,
                codePath,
                path -> new ProcessBuilder( // 通过docker容器进行编译，防止环境不同导致编译结果不同
                        "docker", "run", "--rm",
                        "-v", codePath.getParent() + ":/code", // 挂载父目录
                        "-w", "/code",
                        "gcc:9",
                        "sh", "-c",
                        "g++ -std=c++17 /code/main.cpp -o /code/main && chmod +x /code/main"
                )
        );
    }

    // Python不用编译
//    private boolean compilePythonCode(Submission submission, Path codePath) {
//        return compileCode(
//                submission,
//                codePath,
//                path -> new ProcessBuilder("python", path.toString())
//        );
//    }

    /**
     * 编译Java代码
     *
     * @param submission 提交记录
     * @param codePath   代码文件路径
     * @return 编译是否成功
     */
    private boolean compileJavaCode(Submission submission, Path codePath) {
        return compileCode(
                submission,
                codePath,
                path -> new ProcessBuilder(
                        "javac",
                        "-source", "11",  // 源代码版本
                        "-target", "11",  // 目标字节码版本
                        codePath.toString()
                )
        );
    }

    /**
     * 保存用户代码到临时文件
     *
     * @param code    源代码内容
     * @param path    保存路径
     * @param codeLang 编程语言类型
     * @return 代码文件路径
     * @throws IOException 当文件操作失败时抛出
     */
    private Path saveCode(String code, Path path, String codeLang) throws IOException {
        // 创建多级目录，如果不存在的话
        Files.createDirectories(path);

        // 设置代码文件的名字
        String filename = codeLang.equals("Java 11") || codeLang.equals("java")
                ? "Main.java"
                : codeLang.equals("C++17 (GCC9)") || codeLang.equals("cpp")
                ? "main.cpp"
                : codeLang.equals("Python 3.8") || codeLang.equals("python")
                ? "main.py"
                : "impossible";

        Path codePath = path.resolve(filename);

        // 代码写入
        Files.writeString(codePath, code);

        return codePath;
    }
}
