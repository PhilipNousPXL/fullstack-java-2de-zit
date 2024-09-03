package be.pxl.services.messaging;

import be.pxl.services.domain.Event;
import be.pxl.services.repository.LogbookRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class LogbookEventConsumer {

    private final LogbookRepository logbookRepository;

    public LogbookEventConsumer(LogbookRepository logbookRepository) {
        this.logbookRepository = logbookRepository;
    }

    @RabbitListener(queues = MessagingConfig.LOGBOOK_QUEUE_NAME)
    public void handleMessage(LogbookEvent message) {
        Event event = Event.builder()
                .userName(message.getUserName())
                .dateTime(message.getDateTime())
                .action(message.getType())
                .message(message.getMessage())
                .build();
        logbookRepository.saveAndFlush(event);
    }
}
