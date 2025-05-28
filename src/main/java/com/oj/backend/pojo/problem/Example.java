package com.oj.backend.pojo.problem;

import lombok.Data;

/**
 * JSON数据映射示例类
 * <p>用于演示JSON字段与Java属性的映射关系</p>
 *
 * <p><b>字段映射说明：</b></p>
 * <ul>
 *   <li>dataI → JSON字段: "dataI"</li>
 *   <li>dataO → JSON字段: "dataO"</li>
 * </ul>
 *
 * @apiNote 当JSON字段名与Java属性名不一致时，建议使用{@code @JsonProperty}注解显式声明
 */
@Data
public class Example {
    /**
     * 输入数据字段
     * <p>映射JSON中的 "dataI" 字段</p>
     */
    private String dataI;

    /**
     * 输出数据字段
     * <p>映射JSON中的 "dataO" 字段</p>
     */
    private String dataO;
}
