package be.pxl.services.service;

import be.pxl.services.controller.data.dto.ProductDto;
import be.pxl.services.controller.data.request.ProductRequest;

public interface ProductService {

    ProductDto addProduct(ProductRequest productRequest, String token);

    ProductDto updateProduct(long id, ProductRequest productRequest, String token);

    ProductDto[] getAllProducts();

    ProductDto[] getProductBatch(long[] productIds);

    void deleteProduct(long id, String token);

    ProductDto getProduct(long id);
}
