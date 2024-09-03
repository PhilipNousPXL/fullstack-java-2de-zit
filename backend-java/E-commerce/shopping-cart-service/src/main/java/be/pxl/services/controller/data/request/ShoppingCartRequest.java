package be.pxl.services.controller.data.request;

import lombok.Data;

@Data
public class ShoppingCartRequest {
    private int quantity;
    private ShoppingCartOperation operation;
}
