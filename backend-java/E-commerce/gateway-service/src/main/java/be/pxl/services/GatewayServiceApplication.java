package be.pxl.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/product/**") // request path that comes in the gateway
                        // change the path that goes to the microservice (regex like)
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        // microservice to forward the request to
                        .uri("lb://product-catalog-service")
                )
                .route(p -> p
                        .path("/api/logbook/**")
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        .uri("lb://logbook-service")
                )
                .route(p -> p
                        .path("/api/cart/**") // request path that comes in the gateway
                        // change the path that goes to the microservice (regex like)
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        // microservice to forward the request to
                        .uri("lb://shopping-cart-service")
                )
                .route(p -> p
                        .path("/api/wish-list/**") // request path that comes in the gateway
                        // change the path that goes to the microservice (regex like)
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        // microservice to forward the request to
                        .uri("lb://wish-list-service")
                )
                .build();
    }

    @Configuration
    public static class GatewayCorsConfig extends CorsConfiguration {

        @Bean
        public CorsWebFilter corsFilter() {
            org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
            corsConfiguration.addAllowedOrigin("*");
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
            corsConfiguration.addAllowedHeader("origin");
            corsConfiguration.addAllowedHeader("content-type");
            corsConfiguration.addAllowedHeader("accept");
            corsConfiguration.addAllowedHeader("access-control-allow-origin");
            corsConfiguration.addAllowedHeader("Authorization");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return new CorsWebFilter(source);
        }
    }


}
