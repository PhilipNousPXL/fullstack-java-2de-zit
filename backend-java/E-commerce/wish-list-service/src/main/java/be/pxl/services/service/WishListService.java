package be.pxl.services.service;

import be.pxl.services.client.data.dto.ProductDto;

public interface WishListService {

    void addProductToWishList(String userName, long productId);

    void removeProductFromWishList(String userName, long productId);

    ProductDto[] getWishList(String userName);
}
