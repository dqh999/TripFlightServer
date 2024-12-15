package com.example.railgo.application.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convert(Object source, Class<T> targetType) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.convertValue(source, targetType);
    }
}
