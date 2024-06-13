package com.example.cit.standard.base;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class LongListStringConverter implements AttributeConverter<List<Long>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        return attribute != null ? attribute.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(SPLIT_CHAR)) : null;
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(dbData.split(SPLIT_CHAR))
                .filter(s -> !s.isEmpty())
                .map(Long::valueOf)
                .collect(Collectors.toList()) : null;
    }
}