package be.pxl.services.service;

import be.pxl.services.controller.data.dto.ShoppingCartDto;
import be.pxl.services.controller.data.request.ShoppingCartRequest;

public interface ShoppingCartService {
    ShoppingCartDto updateShoppingCart(String user, long productId, ShoppingCartRequest shoppingCartRequest);

    ShoppingCartDto getShoppingCart(String user);

    void checkout(String user);
}
