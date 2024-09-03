package be.pxl.services.repository;

import be.pxl.services.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogbookRepository extends JpaRepository<Event, Long> {
}
