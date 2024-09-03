package be.pxl.services.client;

import be.pxl.services.client.data.dto.ProductDto;
import be.pxl.services.client.data.request.ProductBatchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

// name of the microservice we want to communicate with
// all the methods here are basically the controller actions
// of other services
// the signature the method should be the same of the controller action
// only the name can be different
@FeignClient(name = "product-catalog-service")
public interface ProductCatalogClient {

    //@RequestMapping(method = RequestMethod.GET, value = "/product/batch")
    @GetMapping(value = "/product/batch")
    ResponseEntity<ProductDto[]> getProductBatch(@RequestBody ProductBatchRequest productBatchRequest);

}

