package com.oj.backend.config.enums;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Difficulty.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Difficulty {
    private Integer min;
    private Integer max;
    private String name;
}
