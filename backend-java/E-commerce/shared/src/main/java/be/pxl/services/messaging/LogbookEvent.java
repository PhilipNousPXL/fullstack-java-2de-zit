package be.pxl.services.messaging;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class LogbookEvent implements Serializable {
    private String userName;
    private LocalDateTime dateTime;
    private Action type;
    private String message;
}