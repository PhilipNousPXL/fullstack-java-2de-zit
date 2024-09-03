package be.pxl.services.controller.data.dto;

import be.pxl.services.domain.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private long id;
    private String name;
    private float price;
    private Category category;
}
