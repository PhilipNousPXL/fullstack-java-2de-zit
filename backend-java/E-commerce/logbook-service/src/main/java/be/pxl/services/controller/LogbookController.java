package be.pxl.services.controller;

import be.pxl.services.controller.data.EventDto;
import be.pxl.services.service.LogbookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logbook")
public class LogbookController {

    private final LogbookService logbookService;

    public LogbookController(LogbookService logbookService) {
        this.logbookService = logbookService;
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getLogbook(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(logbookService.getLogbook(token));
    }
}
