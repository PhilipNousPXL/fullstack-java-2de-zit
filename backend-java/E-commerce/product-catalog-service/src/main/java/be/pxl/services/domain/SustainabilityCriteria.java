package be.pxl.services.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class SustainabilityCriteria {

    private SustainabilityType type;

    // 1 - 5
    private int score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SustainabilityCriteria that = (SustainabilityCriteria) o;
        return score == that.score && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, score);
    }
}
