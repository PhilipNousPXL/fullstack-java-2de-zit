package be.pxl.services.service.impl;

import be.pxl.services.client.ProductCatalogClient;
import be.pxl.services.client.data.dto.ProductDto;
import be.pxl.services.client.data.request.ProductBatchRequest;
import be.pxl.services.domain.WishList;
import be.pxl.services.exception.BadRequestException;
import be.pxl.services.repository.WishListRepository;
import be.pxl.services.service.WishListService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

    private final ProductCatalogClient productCatalogClient;

    public WishListServiceImpl(WishListRepository wishListRepository, ProductCatalogClient productCatalogClient) {
        this.wishListRepository = wishListRepository;
        this.productCatalogClient = productCatalogClient;
    }

    @Override
    public void addProductToWishList(String userName, long productId) {
        WishList wishList = wishListRepository.findById(userName).orElse(new WishList(userName));

        Optional<Long> result = wishList.getProducts().stream().filter((id) -> id == productId).findFirst();

        if (result.isPresent()) {
            log.debug(String.format("Received double wishlist request for id : %d from user: %s", productId, userName));
            throw new BadRequestException("This product is already wishlisted.");
        }

        List<Long> current = new ArrayList<>(wishList.getProducts());
        current.add(productId);
        wishList.setProducts(current);

        wishListRepository.save(wishList);
    }

    @Override
    public void removeProductFromWishList(String userName, long productId) {
        WishList wishList = wishListRepository.findById(userName).orElse(new WishList(userName));

        Optional<Long> result = wishList.getProducts().stream().filter((id) -> id == productId).findFirst();

        if (result.isEmpty()) {
            throw new BadRequestException("This product is not in your wish list.");
        }

        List<Long> current = new ArrayList<>(wishList.getProducts());
        current.remove(productId);
        wishList.setProducts(current);

        wishListRepository.save(wishList);
    }

    @Override
    public ProductDto[] getWishList(String userName) {
        WishList wishList = wishListRepository.findById(userName).orElse(new WishList(userName));

        if (wishList.getProducts().isEmpty()) {
            return new ProductDto[0];
        }

        long[] productIds = wishList.getProducts().stream().mapToLong(Long::longValue).toArray();
        ProductBatchRequest productBatchRequest = new ProductBatchRequest(productIds);

        ProductDto[] products;
        try {
            ResponseEntity<ProductDto[]> response = productCatalogClient.getProductBatch(productBatchRequest);
            products = response.getBody();
        } catch (FeignException.FeignServerException | FeignException.FeignClientException exception) {
            log.warn("Request to catalog-service failed", exception);
            return new ProductDto[0];
        }
        return products;
    }
}
