package be.pxl.services;

import be.pxl.services.messaging.MessagingConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    Exchange exchange() {
        return new TopicExchange(MessagingConfig.EXCHANGE_NAME, false, true);
    }

    @Bean
    public Declarables queues() {
        return new Declarables(
                new Queue(MessagingConfig.SHOPPING_CART_QUEUE_NAME, true, false, false),
                new Queue(MessagingConfig.LOGBOOK_QUEUE_NAME, true, false, false)
        );
    }

    @Bean
    Declarables bindings(Exchange exchange) {
        return new Declarables(
                BindingBuilder.bind(queues().getDeclarablesByType(Queue.class).get(1)).to(exchange).with(MessagingConfig.ROUTING_KEY_LOGBOOK_SERVICE).noargs(),
                BindingBuilder.bind(queues().getDeclarablesByType(Queue.class).get(0)).to(exchange).with(MessagingConfig.ROUTING_KEY_SHOPPING_CART_SERVICE).noargs()
        );
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}
