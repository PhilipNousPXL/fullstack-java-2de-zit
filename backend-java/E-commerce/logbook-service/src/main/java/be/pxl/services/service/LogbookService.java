package be.pxl.services.service;

import be.pxl.services.controller.data.EventDto;

import java.util.List;

public interface LogbookService {

    List<EventDto> getLogbook(String token);
}
