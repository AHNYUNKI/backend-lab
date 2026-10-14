package dev.backendlab.productapi.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductCreateRequest {

    @NotNull(message = "ID는 필수입니다.")
    @Size(min = 1, max = 400)
    private final Long id;

    @NotNull(message = "상품명은 필수입니다.")
    @Size(min = 1, max = 400)
    private final String name;

    @NotNull(message = "가격은 필수입니다.")
    private final int price;

    @NotNull(message = "재고는 필수입니다.")
    private final int stock;

    public ProductCreateRequest(Long id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static ProductCreateRequest of(Long id, String name, int price, int stock) {
        return new ProductCreateRequest(id, name, price, stock);
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
