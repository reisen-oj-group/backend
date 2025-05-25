package com.oj.backend.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Verdict {
    private String id;
    private String description;     // 描述，例如 Wrong Answer
    private String abbr;            // 缩写，例如 WA
    private String color;
}
