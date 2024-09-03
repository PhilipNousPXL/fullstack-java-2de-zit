package be.pxl.services;

import be.pxl.services.controller.data.request.ProductRequest;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.SustainabilityCriteria;
import be.pxl.services.domain.SustainabilityType;
import be.pxl.services.messaging.Action;
import be.pxl.services.messaging.MessagingService;
import be.pxl.services.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.util.List;

import static org.mockito.Mockito.doNothing;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
@ActiveProfiles(value = "test")
public class ProductCatalogServiceApplicationTests {

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
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @MockBean
    private MessagingService messagingService;

    @Test
    void testConnectionIsEstablished() {
        Assertions.assertTrue(mySQLContainer.isCreated());
        Assertions.assertTrue(mySQLContainer.isRunning());
    }

    private final static String BASE_URL = "/product";

    @Test
    void addProductShouldCreateProductViaServiceAndReturnTheResult() throws Exception {
        doNothing().when(messagingService)
                .publishProductUpdate(Mockito.any(Product.class), Mockito.any(Action.class), Mockito.any(String.class));
        SustainabilityCriteria[] sustainabilityCriteria =
                List.of(new SustainabilityCriteria(SustainabilityType.RECYCLED, 3),
                                new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 2))
                        .toArray(SustainabilityCriteria[]::new);
        ProductRequest productRequest = ProductRequest.builder()
                .name("Een product")
                .description("Een description of a product that is a string of any length.")
                .price(23.99f)
                .category(Category.CLOTHES)
                .labels(List.of("First", "Second", "Third").toArray(String[]::new))
                .sustainabilityCriteria(sustainabilityCriteria)
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer username:admin")
                .content(objectMapper.writeValueAsString(productRequest));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        Assertions.assertEquals(1, productRepository.count());
        Mockito.verify(messagingService, Mockito.times(1)).publishProductUpdate(Mockito.any(Product.class), Mockito.eq(Action.CREATE), Mockito.eq("username"));
    }
}

