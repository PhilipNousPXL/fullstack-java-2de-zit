package be.pxl.services;

import be.pxl.services.domain.Event;
import be.pxl.services.messaging.Action;
import be.pxl.services.repository.LogbookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
@ActiveProfiles(value = "test")
public class LogbookServiceApplicationTests {

    @Container
    @ServiceConnection
    private final static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8-debian");

    @DynamicPropertySource
    private static void setMySQLContainer(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LogbookRepository logbookRepository;

    @Test
    void testConnectionIsEstablished() {
        Assertions.assertTrue(mySQLContainer.isCreated());
        Assertions.assertTrue(mySQLContainer.isRunning());
    }

    private final static String BASE_URL = "/logbook";

    @BeforeEach
    void setup() {
        List<Event> eventList = List.of(
                Event.builder()
                        .message("A message")
                        .action(Action.UPDATE)
                        .dateTime(LocalDateTime.now())
                        .userName("1")
                        .build(),
                Event.builder()
                        .message("Another message")
                        .action(Action.UPDATE)
                        .dateTime(LocalDateTime.now())
                        .userName("1")
                        .build(),
                Event.builder()
                        .message("A third message")
                        .action(Action.CREATE)
                        .dateTime(LocalDateTime.now())
                        .userName("1")
                        .build()
        );
        logbookRepository.saveAllAndFlush(eventList);
    }

    @Test
    void getLogbookShouldReturnAllEventsInTheDatabase() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BASE_URL)
                .header("Authorization", "Bearer username:admin");

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(logbookRepository.count()));
    }

    @Test
    void getLogbookShouldReturnUnAuthorizedWhenUserTriedToReadLogbook() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BASE_URL)
                .header("Authorization", "Bearer username:user");

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}

