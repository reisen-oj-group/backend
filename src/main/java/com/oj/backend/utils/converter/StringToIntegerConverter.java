package com.oj.backend.utils.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
