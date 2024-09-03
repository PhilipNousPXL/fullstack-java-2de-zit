package be.pxl.services.client.data.dto;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.SustainabilityCriteria;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ProductDto implements Serializable {
    private long id;
    private String name;
    private float price;
    private String description;
    private SustainabilityCriteria[] sustainabilityCriteria;
    private String[] labels;
    private Category category;
}
