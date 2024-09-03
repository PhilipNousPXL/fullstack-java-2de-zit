package be.pxl.services.controller;

import be.pxl.services.controller.data.dto.ProductDto;
import be.pxl.services.controller.data.request.ProductBatchRequest;
import be.pxl.services.controller.data.request.ProductRequest;
import be.pxl.services.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final static String AUTH_HEADER = "Authorization";
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductRequest productRequest,
                                                 @RequestHeader(value = AUTH_HEADER) String token,
                                                 HttpServletRequest request) {
        //Philip:admin
        //idk:user
        ProductDto productDto = productService.addProduct(productRequest, token);
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(productDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id, @RequestHeader(value = AUTH_HEADER) String token) {
        productService.deleteProduct(id, token);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductRequest productRequest,
                                                    @PathVariable long id,
                                                    @RequestHeader(value = AUTH_HEADER) String token) {
        ProductDto productDto = productService.updateProduct(id, productRequest, token);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<ProductDto[]> getProductBatch(@RequestBody ProductBatchRequest productBatchRequest) {
        ProductDto[] response = productService.getProductBatch(productBatchRequest.getProductIds());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ProductDto[]> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
