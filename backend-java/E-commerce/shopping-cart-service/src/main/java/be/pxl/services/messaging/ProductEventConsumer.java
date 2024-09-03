package be.pxl.services.messaging;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductEventConsumer {

    private final ProductRepository productRepository;

    public ProductEventConsumer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RabbitListener(queues = MessagingConfig.SHOPPING_CART_QUEUE_NAME)
    public void handleMessage(ProductEvent message) {
        switch (message.getAction()) {
            case UPDATE -> updateProduct(message);
            case CREATE -> createProduct(message);
            default -> {
            }
        }
    }

    private void createProduct(ProductEvent message) {
        if (productRepository.existsById(message.getId())) {
            return;
        }

        Product product = Product.builder()
                .id(message.getId())
                .name(message.getName())
                .price(message.getPrice())
                .category(Category.valueOf(message.getCategory()))
                .build();

        productRepository.saveAndFlush(product);
    }

    private void updateProduct(ProductEvent message) {
        Optional<Product> result = productRepository.findById(message.getId());
        if (result.isEmpty()) {
            return;
        }

        Product product = result.get();
        product.setName(message.getName());
        product.setCategory(Category.valueOf(message.getCategory()));
        product.setPrice(message.getPrice());

        productRepository.saveAndFlush(product);
    }

}
