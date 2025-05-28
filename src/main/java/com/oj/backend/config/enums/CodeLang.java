package com.oj.backend.config.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示在线判题系统中编程语言的配置信息。
 * 该类存储支持的编程语言信息，包括语言标识、描述和时限倍率。
 *
 * <p>时限倍率用于为执行速度较慢的语言（如 Python 相比 C++）调整时间限制。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeLang {
    /**
     * 编程语言的唯一标识符。
     * 通常是系统内部使用的短字符串（例如 "cpp"、"java"、"python"）。
     */
    private String id;

    /**
     * 编程语言的人类可读描述。
     * 通常包括语言名称和版本信息（例如 "C++17 (GCC9)"、"Java 11"、"Python 3.8"）。
     */
    private String description;

    /**
     * 该编程语言的时限倍率。
     * 该值用作问题时间限制的乘数，以考虑不同语言的性能差异。
     * 例如，比率为 2 表示该语言的时间限制是标准值的两倍。
     *
     * <p>比率为 1 表示该语言具有标准性能，更高的值表示性能较慢。
     */
    private Integer ratio;
}

