package be.pxl.services.service.impl;

import be.pxl.services.controller.data.EventDto;
import be.pxl.services.repository.LogbookRepository;
import be.pxl.services.service.LogbookService;
import be.pxl.services.service.auth.AuthorizationService;
import be.pxl.services.service.auth.Role;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class LogbookServiceImpl implements LogbookService {

    private final LogbookRepository logbookRepository;

    private final AuthorizationService authorizationService;

    public LogbookServiceImpl(LogbookRepository logbookRepository, AuthorizationService authorizationService) {
        this.logbookRepository = logbookRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public List<EventDto> getLogbook(String token) {
        authorizationService.authorize(token, Role.ADMIN);

        return logbookRepository.findAll().stream().map((event) -> EventDto.builder()
                        .message(event.getMessage())
                        .action(event.getAction())
                        .dateTime(event.getDateTime())
                        .userName(event.getUserName())
                        .build()
                ).sorted(Comparator.comparing(EventDto::getDateTime))
                .toList();
    }
}
