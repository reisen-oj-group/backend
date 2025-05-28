package com.oj.backend.config.enums;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User lang.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLang {
    private String id;
    private String description;     // 语言描述，例如 “中文” 或者 “English”
}
