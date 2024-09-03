package be.pxl.services.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Converter
public class IntegerListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<Long> integerList) {
        if (integerList == null || integerList.isEmpty()) {
            return "";
        }
        String[] strings = integerList.stream().map((Object::toString)).toArray(String[]::new);
        return String.join(SPLIT_CHAR, strings);
    }

    @Override
    public List<Long> convertToEntityAttribute(String string) {
        if (string.isEmpty() || string.isBlank()) {
            return emptyList();
        }
        return Arrays.stream(string.split(SPLIT_CHAR)).map(Long::parseLong).collect(Collectors.toList());
    }
}
