package be.pxl.services.client.data.request;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.SustainabilityCriteria;
import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private float price;
    private String description;
    private SustainabilityCriteria[] sustainabilityCriteria;
    private String[] labels;
    private Category category;
}
