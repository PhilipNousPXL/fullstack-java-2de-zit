package be.pxl.services.client;

import be.pxl.services.controller.data.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//name of the microservice we want to communicate with
//all the methods here are basically the controller actions
//of other services
//the signature the method should be the same of the controller action
//only the name can be different
//TODO: go via the gateway
@FeignClient(name = "product-catalog-service", path = "/product")
public interface ProductCatalogClient {
    @GetMapping(path = "/{id}")
    ResponseEntity<ProductDto> getProduct(@PathVariable long id);
}

