package be.pxl.services.controller.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingCartDto {
    private ShoppingCartItemDto[] items;
}
