package com.oj.backend.utils.docker;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.oj.backend.service.config.impl.ConfigServiceImpl.FIXED_CODE_LANGS;

/**
 * Docker评测运行器
 * <p>
 * 提供通过Docker容器安全运行用户代码的功能，支持多种编程语言的隔离执行。
 * 主要特性包括：
 * <ul>
 *   <li>资源限制（CPU、内存、运行时间）</li>
 *   <li>安全隔离（无网络、只读文件系统、非root用户）</li>
 *   <li>多语言支持（Java、C++、Python等）</li>
 *   <li>运行结果收集（输出、错误、时间、内存等）</li>
 * </ul>
 * </p>
 */
@Data
public class DockerJudgeRunner {
    /**
     * 在Docker容器中运行用户代码
     *
     * @param tmpDir      临时工作目录路径，包含用户代码文件
     * @param lang        编程语言类型（java/cpp/python）
     * @param dataI       程序输入数据
     * @param limitTime   时间限制（单位：秒）
     * @param limitMemory 内存限制（单位：MB）
     * @return 包含运行结果的DockerRunResult对象，包含：
     *         <ul>
     *           <li>程序输出(dataO)</li>
     *           <li>错误信息(error)</li>
     *           <li>运行时间(timeUsed)</li>
     *           <li>内存使用(memoryUsed)</li>
     *           <li>退出码(exitCode)</li>
     *           <li>判定结果(verdict)</li>
     *         </ul>
     * @throws RuntimeException 当Docker执行或IO操作出现异常时抛出
     */
    public static DockerRunResult runContainer(Path tmpDir,
                                               String lang,
                                               String dataI,
                                               Integer limitTime,
                                               Integer limitMemory) {
        // 创建一个运行结果供返回
        DockerRunResult result = new DockerRunResult();
        Process process = null;

        try {
            // 构造docker运行命令
            List<String> command = buildDockerCommand(tmpDir, lang, limitTime, limitMemory);
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            process = processBuilder.start();

            // os会在try-with-resources语句结束后自动释放
            try (OutputStream os = process.getOutputStream()) {  // 向文件输入数据getOutputStream是父进程对子进程的输出流（就是相当于在终端打字）
                os.write(dataI.getBytes(StandardCharsets.UTF_8));
            }

            // 设置最多等待limitTime时间，单位是秒，可更改，设置了1s冗余时间
            boolean finished = process.waitFor(limitTime + 1, TimeUnit.SECONDS);

            // 根据运行结果设置result，子进程输出到父进程，父进程是输入流，所以input
            result.setDataO(readStream(process.getInputStream()));
            result.setError(readStream(process.getErrorStream()));
            result.setExitCode(process.exitValue());

            // 注意，此处设置时间单位为秒
            result.setTimeUsed(     // 计算进程运行时间 (Duration.between)
                    (int) Duration.between(
                            process.info().startInstant().get(),    // 进程开始时间
                            Instant.now()                           // 当前时间
                    ).toSeconds()
            );

            result.setMemoryUsed(getMemoryUsed());

            if (!finished) {
                result.setVerdict("TLE");
                process.destroyForcibly();
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (process != null && process.isAlive()) {
                process.destroyForcibly();
            }
        }

        return result;
    }

    /**
     * 获取当前容器内存使用量
     *
     * @return 内存使用量（单位：MB），出错时返回0
     */
    private static Integer getMemoryUsed() {
        try {
            // 直接读取当前容器的内存统计（单位：MB）
            String memStr = new String(Files.readAllBytes(
                    Paths.get("/sys/fs/cgroup/memory/memory.usage_in_bytes")
            )).trim();
            return (int) (Long.parseLong(memStr) / 1024 / 1024);
        } catch (Exception e) {
            return 0; // 如果出错返回0
        }
    }


    /**
     * 读取输入流内容
     *
     * @param inputStream 要读取的输入流
     * @return 流内容的字符串表示
     * @throws IOException 当读取失败时抛出
     */
    private static String readStream(InputStream inputStream) throws IOException {
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    /**
     * 构建Docker运行命令
     *
     * @param tmpDir      临时工作目录路径
     * @param lang        编程语言类型
     * @param limitTime   时间限制（单位：秒）
     * @param limitMemory 内存限制（单位：MB）
     * @return 构造好的Docker命令参数列表
     * @throws IllegalArgumentException 当不支持的语言类型传入时抛出
     */
    private static List<String> buildDockerCommand(Path tmpDir, String lang, Integer limitTime, Integer limitMemory) {
        List<String> cmd = new ArrayList<>(
                Arrays.asList(
                        "docker", "run", "--rm", "-i",   // 启动一个新容器,容器退出后自动删除（避免残留）
                        "-v", tmpDir + ":/code",   // 将工作目录挂载到docker的/code文件夹下
                        "-w", "/code",             // 设置容器工作目录
                        "--network=none",          // 禁用网络
                        "--memory=" + limitMemory + "m",          // 限制容器内存
                        "--cpus=1",                // 限制容器最多使用 1 个 CPU 核
                        "--read-only",             // 只读
                        "--user=1000",             // 用户以非root用户（UID为1000）访问
                        "--cap-drop=ALL"           // 禁用linux特权能力（如调用 reboot 重启宿主机）
                )
        );

        switch (lang.toLowerCase()) {
            case ("java"):
                cmd.addAll(Arrays.asList(
                        "openjdk:11",   // 使用java11的docker
                        "sh", "-c",
                        "timeout " + (limitTime * FIXED_CODE_LANGS.get(lang).getRatio()) +  // 限制运行时间,为运行慢的语言乘以倍率
                        " java -Xmx"+ (limitMemory - 16) + "m Main" //设置启动参数，设置 JVM 的 最大堆内存，同时为非堆内存预留16MB空间
                ));
                break;
            case ("cpp"):
                cmd.addAll(Arrays.asList(
                        "gcc:9",
                        "sh", "-c",     // 使用shell执行timeout命令，并将输入传入到程序内部
                        "timeout " + (limitTime * FIXED_CODE_LANGS.get(lang).getRatio()) + // 必须放在镜像名的后面，不然会被当成镜像
                        " ./main"
                ));
                break;
            case ("python"):
                cmd.addAll(Arrays.asList(
                        "python:3.8",
                        "sh", "-c",
                        "timeout "+ (limitTime * FIXED_CODE_LANGS.get(lang).getRatio()) +
                        " python -B main.py"    // 禁止生成 __pycache__ 目录
                ));
                break;
            default:
                throw new IllegalArgumentException("Unsupported Language: " + lang);
        }
        return cmd;
    }

    /**
     * Docker运行结果封装类
     * <p>
     * 包含程序运行的各类指标数据和状态信息，用于评测结果判定。
     * </p>
     */
    @Data
    public static class DockerRunResult {
        private String error = "";           // 错误信息
        private String verdict = "AC";      // 运行结果默认AC
        private String dataO = "";          // 运行输出默认无
        private Integer timeUsed = 0;       // 运行时间默认0
        private Integer memoryUsed = 0;     // 运行使用内存默认0
        private Integer exitCode = 0;       // 退出代码默认0
    }
}
