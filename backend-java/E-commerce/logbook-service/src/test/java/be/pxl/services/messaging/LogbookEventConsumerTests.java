package be.pxl.services.messaging;

import be.pxl.services.domain.Event;
import be.pxl.services.repository.LogbookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class LogbookEventConsumerTests {
    @Mock
    private LogbookRepository logbookRepository;
    @InjectMocks
    private LogbookEventConsumer eventConsumer;


    @Test
    void consumerShouldSaveEventToDb() {
        LogbookEvent logbookEvent = LogbookEvent.builder()
                .message("A message")
                .type(Action.UPDATE)
                .dateTime(LocalDateTime.now())
                .userName("user1")
                .build();

        Mockito.when(logbookRepository.saveAndFlush(Mockito.any(Event.class))).thenReturn(null);

        eventConsumer.handleMessage(logbookEvent);

        Mockito.verify(logbookRepository, Mockito.times(1)).saveAndFlush(Mockito.any(Event.class));
    }

}
