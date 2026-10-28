package dev.backendlab.productapi.api;

import dev.backendlab.productapi.api.request.ProductCreateRequest;
import dev.backendlab.productapi.application.ProductService;
import dev.backendlab.productapi.application.response.ProductResponse;
import dev.backendlab.productapi.exception.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/v1/products")
    public ApiResponse<ProductResponse> create(@RequestBody ProductCreateRequest request) {
        return ApiResponse.ok(productService.createProduct(request));
    }

    @GetMapping("/v1/products/{id}")
    public ApiResponse<ProductResponse> findProduct(@PathVariable Long id) {
        return ApiResponse.ok(productService.findProduct(id));
    }

}
