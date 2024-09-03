package be.pxl.services;

import be.pxl.services.openapi.OpenApiBearerTokenConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WishListServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WishListServiceApplication.class, args);
    }

    @Bean
    public OpenApiBearerTokenConfig idk() {
        return new OpenApiBearerTokenConfig();
    }
}


