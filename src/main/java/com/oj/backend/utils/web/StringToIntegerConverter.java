package com.oj.backend.utils.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 字符串到整数的转换器（带前缀处理）
 * <p>
 * 用于处理带有特定前缀（如"P"）的字符串ID，将其转换为整型数值。
 * 主要用途是转换类似"P123"这样的带前缀ID为纯数字123。
 * </p>
 *
 * <p>典型使用场景：</p>
 * <ul>
 *   <li>处理前端传来的带前缀的业务ID</li>
 *   <li>转换数据库中的复合主键格式</li>
 *   <li>作为Spring MVC的参数转换器</li>
 * </ul>
 *
 * @see org.springframework.core.convert.converter.Converter
 */
@Component
public class StringToIntegerConverter implements Converter<String, Integer> {

    /**
     * 执行带前缀字符串到整数的转换
     *
     * @param s 要转换的字符串，格式应为"P"开头后跟数字（例如："P123"）
     * @return 去除前缀后的数字值
     * @throws IllegalArgumentException 当出现以下情况时抛出：
     *          <ul>
     *            <li>输入字符串为null或空字符串</li>
     *            <li>字符串不以"P"开头</li>
     *            <li>"P"后的部分不是有效整数</li>
     *          </ul>
     *
     * @apiNote 示例：
     * <pre>{@code
     * // 将"P1234"转换为1234
     * Integer id = converter.convert("P1234");
     * }</pre>
     */
    @Override
    public Integer convert(@NonNull String s) {
        if(s.startsWith("P")){
            return Integer.parseInt(s.substring(1));
        }
        throw new IllegalArgumentException("Invalid ID format: " + s);
    }
}
