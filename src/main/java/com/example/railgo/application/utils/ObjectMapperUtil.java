package com.example.railgo.application.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convert(Object source, Class<T> targetType) {
        return objectMapper.convertValue(source, targetType);
    }
}
