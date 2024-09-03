package be.pxl.services.messaging.impl;

import be.pxl.services.domain.Product;
import be.pxl.services.messaging.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MessagingServiceImpl implements MessagingService {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
    private final RabbitTemplate rabbitTemplate;

    public MessagingServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishProductUpdate(Product product, Action action, String user) {
        LocalDateTime now = LocalDateTime.now();
        String text = "";
        switch (action) {
            case CREATE ->
                    text = String.format("New product [%s] created by %s at %s", product.getName(), user, DATE_TIME_FORMAT.format(now));
            case DELETE ->
                    text = String.format("Product [%s] deleted by %s at %s", product.getName(), user, DATE_TIME_FORMAT.format(now));
            case UPDATE ->
                    text = String.format("Product [%s] updated by %s at %s", product.getName(), user, DATE_TIME_FORMAT.format(now));
        }

        LogbookEvent logbookEvent = LogbookEvent.builder()
                .message(text)
                .dateTime(now)
                .userName(user)
                .type(action)
                .build();

        ProductEvent productEvent = ProductEvent.builder()
                .id(product.getId())
                .price(product.getPrice())
                .action(action)
                .category(product.getCategory().name())
                .name(product.getName())
                .build();

        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE_NAME, MessagingConfig.ROUTING_KEY_SHOPPING_CART_SERVICE, productEvent);
        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE_NAME, MessagingConfig.ROUTING_KEY_LOGBOOK_SERVICE, logbookEvent);
    }

}
