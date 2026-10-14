package dev.backendlab.productapi.domain.repository;

import dev.backendlab.productapi.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public void save(Product product) {
        products.add(product);
    }

    public Product findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean existsById(Long id) {
        return products.stream()
                .anyMatch(product -> product.getId().equals(id));
    }

    public boolean existsByName(String name) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(name));
    }

    public void deleteAll() {
        products.clear();
    }

}
