package be.pxl.services.domain;

import be.pxl.services.util.StringListConverter;
import be.pxl.services.util.SustainabilityCriteriaConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private float price;

    @Column(length = 65555)
    private String description;

    @Convert(converter = SustainabilityCriteriaConverter.class)
    @Column(name = "critera", nullable = false)
    private List<SustainabilityCriteria> sustainabilityCriteria;

    @Convert(converter = StringListConverter.class)
    @Column(name = "label", nullable = false)
    private List<String> labels;

    @Enumerated(value = EnumType.STRING)
    private Category category;
}
