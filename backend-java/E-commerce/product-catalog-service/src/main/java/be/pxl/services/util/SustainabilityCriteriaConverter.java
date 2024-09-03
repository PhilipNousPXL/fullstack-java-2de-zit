package be.pxl.services.util;

import be.pxl.services.domain.SustainabilityCriteria;
import be.pxl.services.domain.SustainabilityType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Converter
public class SustainabilityCriteriaConverter implements AttributeConverter<List<SustainabilityCriteria>, String> {
    private static final String SPLIT_CHAR = ";";
    private static final String SPLIT_CHAR_DATA = ",";

    @Override
    public String convertToDatabaseColumn(List<SustainabilityCriteria> criteriaList) {
        if (criteriaList == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (SustainabilityCriteria sustainabilityCriteria : criteriaList) {
            stringBuilder.append(sustainabilityCriteria.getType());
            stringBuilder.append(SPLIT_CHAR_DATA);
            stringBuilder.append(sustainabilityCriteria.getScore());
            if (criteriaList.indexOf(sustainabilityCriteria) != criteriaList.size() - 1) {
                stringBuilder.append(SPLIT_CHAR);
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public List<SustainabilityCriteria> convertToEntityAttribute(String string) {
        if (string == null || string.isEmpty() || string.isBlank()) {
            return emptyList();
        }

        String[] criteriaList = string.split(SPLIT_CHAR);
        List<SustainabilityCriteria> sustainabilityCriteria = new ArrayList<>();
        for (String criteria : criteriaList) {
            String[] data = criteria.split(SPLIT_CHAR_DATA);
            int score = Integer.parseInt(data[1]);
            SustainabilityType type = SustainabilityType.valueOf(data[0]);
            sustainabilityCriteria.add(new SustainabilityCriteria(type, score));
        }
        return sustainabilityCriteria;
    }
}
