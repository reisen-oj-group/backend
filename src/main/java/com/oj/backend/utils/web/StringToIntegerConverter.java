package com.oj.backend.utils.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * The type String to integer converter.
 */
@Component
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String s) {
        if(s.startsWith("P")){
            return Integer.parseInt(s.substring(1));
        }
        throw new IllegalArgumentException("Invalid ID format: " + s);
    }
}
