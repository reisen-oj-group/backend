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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.oj.backend.service.config.impl.ConfigServiceImpl.FIXED_CODE_LANGS;

@Data
public class DockerJudgeRunner {
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
            if (process != null && process.isAlive()){
                process.destroyForcibly();
            }
        }

        return result;
    }

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


    // 将
    private static String readStream(InputStream inputStream) throws IOException {
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    private static List<String> buildDockerCommand(Path tmpDir, String lang, Integer limitTime, Integer limitMemory) {
        List<String> cmd = new ArrayList<>(
                Arrays.asList(
                        "docker", "run", "--rm",   // 启动一个新容器,容器退出后自动删除（避免残留）
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
                        "timeout", String.valueOf(limitTime * FIXED_CODE_LANGS.get(lang).getRatio()),  // 限制运行时间,为运行慢的语言乘以倍率
                        "java", "-Xmx" + (limitMemory - 16) + "m", "Main" //设置启动参数，设置 JVM 的 最大堆内存，同时为非堆内存预留16MB空间
                ));
                break;
            case ("cpp"):
                cmd.addAll(Arrays.asList(
                        "gcc:9",
                        "timeout", String.valueOf(limitTime * FIXED_CODE_LANGS.get(lang).getRatio()), // 必须放在镜像名的后面，不然会被当成镜像
                        "./main"
                ));
                break;
            case ("python"):
                cmd.addAll(Arrays.asList(
                        "python:3.8",
                        "timeout", String.valueOf(limitTime * FIXED_CODE_LANGS.get(lang).getRatio()),
                        "python", "-B", "main.py"   // 禁止生成 __pycache__ 目录
                ));
                break;
            default:
                throw new IllegalArgumentException("Unsupported Language: " + lang);
        }
        return cmd;
    }

    @Data
    public static class DockerRunResult {
        public String error = "";           // 错误信息
        private String verdict = "AC";      // 运行结果默认AC
        private String dataO = "";          // 运行输出默认无
        private Integer timeUsed = 0;       // 运行时间默认0
        private Integer memoryUsed = 0;     // 运行使用内存默认0
        private Integer exitCode = 0;       // 退出代码默认0
    }
}
