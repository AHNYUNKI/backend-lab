package dev.backendlab.productapi.application;

import dev.backendlab.productapi.api.request.ProductCreateRequest;
import dev.backendlab.productapi.application.response.ProductResponse;
import dev.backendlab.productapi.domain.Product;
import dev.backendlab.productapi.domain.repository.ProductRepository;
import dev.backendlab.productapi.exception.BusinessException;
import dev.backendlab.productapi.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dev.backendlab.productapi.exception.ErrorCode.*;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductCreateRequest request) {
        if (productRepository.existsById(request.getId())) {
            throw new BusinessException(ALREADY_EXISTS_PRODUCT_ID);
        }

        if (productRepository.existsByName(request.getName())) {
            throw new BusinessException(ALREADY_EXISTS_PRODUCT_NAME);
        }

        Product product = Product.create(request.getId(), request.getName(), request.getPrice(), request.getStock());

        productRepository.save(product);
        return ProductResponse.from(product);
    }

}
