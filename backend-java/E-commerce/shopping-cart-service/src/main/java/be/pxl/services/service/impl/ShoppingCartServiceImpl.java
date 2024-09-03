package be.pxl.services.service.impl;

import be.pxl.services.controller.data.dto.ProductDto;
import be.pxl.services.controller.data.dto.ShoppingCartDto;
import be.pxl.services.controller.data.dto.ShoppingCartItemDto;
import be.pxl.services.controller.data.request.ShoppingCartOperation;
import be.pxl.services.controller.data.request.ShoppingCartRequest;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.ShoppingCart;
import be.pxl.services.domain.ShoppingCartItem;
import be.pxl.services.exception.BadRequestException;
import be.pxl.services.exception.ResourceNotFoundException;
import be.pxl.services.repository.ProductRepository;
import be.pxl.services.repository.ShoppingCartItemRepository;
import be.pxl.services.repository.ShoppingCartRepository;
import be.pxl.services.service.ShoppingCartService;
import be.pxl.services.service.auth.AuthorizationService;
import be.pxl.services.service.auth.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final AuthorizationService authorizationService;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, AuthorizationService authorizationService, ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.authorizationService = authorizationService;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    @Override
    public ShoppingCartDto updateShoppingCart(String user, long productId, ShoppingCartRequest shoppingCartRequest) {
        final Optional<ShoppingCart> result = shoppingCartRepository.findById(user);

        ShoppingCart shoppingCart = result.orElseGet(() -> {
            log.info("Creating new shopping cart for user: " + user);
            return new ShoppingCart(user);
        });

        final ShoppingCartOperation operation = shoppingCartRequest.getOperation();
        if (operation == ShoppingCartOperation.ADD) {
            shoppingCart = addToCart(shoppingCart, productId, shoppingCartRequest.getQuantity());
        } else {
            shoppingCart = removeFromCart(shoppingCart, productId, shoppingCartRequest.getQuantity());
        }

        return mapToShoppingCartDto(shoppingCart);
    }

    private ShoppingCart removeFromCart(ShoppingCart shoppingCart, long productId, int quantity) {
        Optional<ShoppingCartItem> existingItem = shoppingCart.getItems().stream().filter((item) -> item.getProduct().getId() == productId).findFirst();
        if (existingItem.isEmpty()) {
            throw new BadRequestException(String.format("Product with id %d is not in the cart.", productId));
        }

        ShoppingCartItem shoppingCartItem = existingItem.get();
        if (shoppingCartItem.getQuantity() <= quantity) {
            shoppingCart.getItems().remove(shoppingCartItem);
            ShoppingCart newCart = shoppingCartRepository.saveAndFlush(shoppingCart);
            shoppingCartItemRepository.deleteById(shoppingCartItem.getId());
            return newCart;
        }

        shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() - quantity);
        return shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart addToCart(ShoppingCart shoppingCart, long productId, int quantity) {
        Optional<ShoppingCartItem> existingItem = shoppingCart.getItems().stream().filter((item) -> item.getProduct().getId() == productId).findFirst();
        if (existingItem.isPresent()) {
            ShoppingCartItem shoppingCartItem = existingItem.get();
            shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + quantity);
        } else {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id %d was not found.", productId)));
            ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder()
                    .quantity(quantity)
                    .product(product)
                    .build();
            shoppingCart.getItems().add(shoppingCartItem);
        }

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto getShoppingCart(String token) {
        String userName = authorizationService.authorize(token, Role.USER);

        Optional<ShoppingCart> result = shoppingCartRepository.findById(userName);

        ShoppingCart shoppingCart = result.orElseGet(() -> new ShoppingCart(userName));

        return mapToShoppingCartDto(shoppingCart);
    }

    @Override
    public void checkout(String user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(user)
                .orElseThrow(() -> new ResourceNotFoundException("You dont have a shopping cart yet."));

        ShoppingCartItem[] itemsToRemove = shoppingCart.getItems().toArray(ShoppingCartItem[]::new);
        for (ShoppingCartItem item : itemsToRemove) {
            shoppingCart.getItems().remove(item);
        }

        shoppingCartRepository.saveAndFlush(shoppingCart);
        shoppingCartItemRepository.deleteAllByIdInBatch(Arrays.stream(itemsToRemove).map(ShoppingCartItem::getId).toList());
        // DO something
    }

    private ShoppingCartDto mapToShoppingCartDto(ShoppingCart shoppingCart) {
        ShoppingCartItemDto[] items = shoppingCart.getItems().stream().map((item) -> {
            ProductDto productDto = ProductDto.builder()
                    .category(item.getProduct().getCategory())
                    .price(item.getProduct().getPrice())
                    .name(item.getProduct().getName())
                    .id(item.getProduct().getId())
                    .build();

            return ShoppingCartItemDto.builder()
                    .quantity(item.getQuantity())
                    .product(productDto)
                    .build();
        }).toArray(ShoppingCartItemDto[]::new);

        return ShoppingCartDto.builder()
                .items(items)
                .build();
    }
}
