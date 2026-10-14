package dev.backendlab.productapi.application.response;

import dev.backendlab.productapi.domain.Product;

public class ProductResponse {

    private final Long id;

    private final String name;

    private final int price;

    private final int stock;

    private ProductResponse(Long id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static ProductResponse of(Long id, String name, int price, int stock) {
        return new ProductResponse(id, name, price, stock);
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

}
