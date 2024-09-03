package be.pxl.services.service.impl;

import be.pxl.services.controller.data.dto.ProductDto;
import be.pxl.services.controller.data.request.ProductRequest;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.SustainabilityCriteria;
import be.pxl.services.exception.ResourceNotFoundException;
import be.pxl.services.messaging.Action;
import be.pxl.services.messaging.MessagingService;
import be.pxl.services.repository.ProductRepository;
import be.pxl.services.service.ProductService;
import be.pxl.services.service.auth.AuthorizationService;
import be.pxl.services.service.auth.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MessagingService messagingService;
    private final AuthorizationService authorizationService;

    public ProductServiceImpl(ProductRepository productRepository, MessagingService messagingService, AuthorizationService authorizationService) {
        this.productRepository = productRepository;
        this.messagingService = messagingService;
        this.authorizationService = authorizationService;
    }

    @Override
    public ProductDto addProduct(ProductRequest productRequest, String token) {
        String user = authorizationService.authorize(token, Role.ADMIN);

        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .category(productRequest.getCategory())
                .labels(Arrays.asList(productRequest.getLabels()))
                .sustainabilityCriteria(Arrays.stream(productRequest.getSustainabilityCriteria()).toList())
                .build();

        Product savedProduct = productRepository.saveAndFlush(product);

        messagingService.publishProductUpdate(savedProduct, Action.CREATE, user);

        return mapToProductDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(long id, ProductRequest productRequest, String token) {
        String user = authorizationService.authorize(token, Role.ADMIN);

        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id %d was not found.", id)));

        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setSustainabilityCriteria(Arrays.stream(productRequest.getSustainabilityCriteria()).toList());
        product.setLabels(Arrays.asList(productRequest.getLabels()));

        Product savedProduct = productRepository.saveAndFlush(product);

        messagingService.publishProductUpdate(product, Action.UPDATE, user);

        return mapToProductDto(savedProduct);
    }

    @Override
    public void deleteProduct(long id, String token) {
        String user = authorizationService.authorize(token, Role.ADMIN);

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            log.warn(String.format("Product with id %d was not found.", id));
            throw new ResourceNotFoundException(String.format("Product with id %d was not found.", id));
        }
        productRepository.deleteById(id);

        messagingService.publishProductUpdate(product.get(), Action.DELETE, user);
    }

    @Override
    public ProductDto[] getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductDto).toArray(ProductDto[]::new);
    }

    @Override
    public ProductDto[] getProductBatch(long[] productIds) {
        log.info("Received batch request from wish-list service");
        List<Long> ids = Arrays.stream(productIds).boxed().toList();
        List<Product> products = productRepository.findAllById(ids);
        return products.stream().map(this::mapToProductDto).toArray(ProductDto[]::new);
    }


    @Override
    public ProductDto getProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id %d was not found.", id)));
        return mapToProductDto(product);
    }

    private ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .labels(product.getLabels().toArray(String[]::new))
                .category(product.getCategory())
                .sustainabilityCriteria(product.getSustainabilityCriteria().toArray(SustainabilityCriteria[]::new))
                .build();
    }
}
