package be.pxl.services.service;

import be.pxl.services.controller.data.dto.ProductDto;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.SustainabilityCriteria;
import be.pxl.services.domain.SustainabilityType;
import be.pxl.services.exception.ResourceNotFoundException;
import be.pxl.services.messaging.Action;
import be.pxl.services.messaging.MessagingService;
import be.pxl.services.repository.ProductRepository;
import be.pxl.services.service.auth.AuthorizationService;
import be.pxl.services.service.auth.Role;
import be.pxl.services.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private MessagingService messagingService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void deleteProductShouldDeleteProductIfFoundAndPublishMessage() {
        List<SustainabilityCriteria> sustainabilityCriteria =
                List.of(new SustainabilityCriteria(SustainabilityType.RECYCLED, 3),
                        new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 2));
        Product product = Product.builder().name("Een product")
                .description("Een description of a product that is a string of any length.")
                .price(23.99f)
                .category(Category.CLOTHES)
                .labels(List.of("First", "Second", "Third"))
                .sustainabilityCriteria(sustainabilityCriteria)
                .build();

        Mockito.when(productRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(product));
        Mockito.when(authorizationService.authorize(Mockito.any(String.class), Mockito.any(Role.class))).thenReturn("user");
        doNothing().when(messagingService)
                .publishProductUpdate(Mockito.any(Product.class), Mockito.any(Action.class), Mockito.any(String.class));
        String token = "token";
        long id = 2;

        productService.deleteProduct(id, token);

        Mockito.verify(authorizationService, Mockito.times(1)).authorize(token, Role.ADMIN);
        Mockito.verify(productRepository, Mockito.times(1)).findById(id);
        Mockito.verify(messagingService, Mockito.times(1)).publishProductUpdate(product, Action.DELETE, "user");
    }

    @Test
    void deleteProductShouldThrowExceptionWhenProductDoesntExist() {
        Mockito.when(productRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());
        Mockito.when(authorizationService.authorize(Mockito.any(String.class), Mockito.any(Role.class))).thenReturn("user");
        String token = "token";
        long id = 2;

        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(id, token));

        Mockito.verify(authorizationService, Mockito.times(1)).authorize(token, Role.ADMIN);
        Mockito.verify(productRepository, Mockito.times(1)).findById(id);
        Mockito.verify(messagingService, Mockito.never()).publishProductUpdate(Mockito.any(Product.class), Mockito.any(Action.class), Mockito.any(String.class));
    }

    @Test
    void getAllProductsShouldReturnAllProductAndMapThemToDtos() {
        List<SustainabilityCriteria> sustainabilityCriteria =
                List.of(new SustainabilityCriteria(SustainabilityType.RECYCLED, 3),
                        new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 2));
        Product product = Product.builder().name("Een product")
                .description("Een description of a product that is a string of any length.")
                .price(23.99f)
                .category(Category.CLOTHES)
                .labels(List.of("First", "Second", "Third"))
                .sustainabilityCriteria(sustainabilityCriteria)
                .build();
        List<Product> products = List.of(product, product, product);


        when(productRepository.findAll()).thenReturn(products);


        ProductDto[] result = productService.getAllProducts();

        Assertions.assertEquals(3, result.length);
        Assertions.assertSame(products.get(0).getName(), result[0].getName());
        Mockito.verify(authorizationService, Mockito.never()).authorize(Mockito.any(String.class), Mockito.any(Role.class));
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }
}

