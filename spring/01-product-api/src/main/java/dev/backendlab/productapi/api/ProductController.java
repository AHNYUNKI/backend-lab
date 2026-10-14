package dev.backendlab.productapi.api;

import dev.backendlab.productapi.api.request.ProductCreateRequest;
import dev.backendlab.productapi.application.ProductService;
import dev.backendlab.productapi.application.response.ProductResponse;
import dev.backendlab.productapi.exception.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
