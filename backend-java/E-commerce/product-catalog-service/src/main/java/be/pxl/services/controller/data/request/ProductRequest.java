package be.pxl.services.controller.data.request;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.SustainabilityCriteria;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    private String name;
    private float price;
    private String description;
    private SustainabilityCriteria[] sustainabilityCriteria;
    private String[] labels;
    private Category category;
}
