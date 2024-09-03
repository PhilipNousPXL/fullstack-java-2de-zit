package be.pxl.services.messaging;

public class MessagingConfig {
    public static final String LOGBOOK_QUEUE_NAME = "logbook-queue";
    public static final String EXCHANGE_NAME = "e-commerce-exchange";
    public static final String ROUTING_KEY_LOGBOOK_SERVICE = String.format("%s.%s", EXCHANGE_NAME, LOGBOOK_QUEUE_NAME);
    public static final String SHOPPING_CART_QUEUE_NAME = "shopping-cart-queue";
    public static final String ROUTING_KEY_SHOPPING_CART_SERVICE = String.format("%s.%s", EXCHANGE_NAME, SHOPPING_CART_QUEUE_NAME);
}
