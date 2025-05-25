package com.oj.backend.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeLang {
    private String id;
    private String description;     // 语言描述，例如 C++11 (GCC9)
    private Integer ratio;          // 时限比率，用于给更慢的语言乘上倍率
}
