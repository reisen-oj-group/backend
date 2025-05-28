package com.oj.backend.config.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Code lang.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeLang {
    private String id;
    private String description;     // 语言描述，C++17 (GCC9) Java 11 Python 3.8
    private Integer ratio;          // 时限比率，用于给更慢的语言乘上倍率
}
