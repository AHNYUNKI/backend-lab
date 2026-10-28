package dev.backendlab.productapi.domain.repository;

import dev.backendlab.productapi.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public void save(Product product) {
        products.add(product);
    }

    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
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
