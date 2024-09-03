package be.pxl.services.controller.data;

import be.pxl.services.messaging.Action;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventDto {
    private String userName;
    private LocalDateTime dateTime;
    private Action action;
    private String message;
}
