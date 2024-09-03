package be.pxl.services;

import be.pxl.services.openapi.OpenApiBearerTokenConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class LogbookServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogbookServiceApplication.class, args);
    }

    @Bean
    public OpenApiBearerTokenConfig myConfig() {
        return new OpenApiBearerTokenConfig();
    }
}
