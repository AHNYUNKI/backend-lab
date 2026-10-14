package dev.backendlab.productapi.domain;

public class Product {

    private Long id;

    private String name;

    private int price;

    private int stock;

    private Product(Long id, String name, int price, int stock) {
        if (id == null) {
            throw new IllegalArgumentException("상품 ID는 필수 입니다.");
        }
        if (name == null) {
            throw new IllegalArgumentException("상품 이름은 필수 입니다.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("상품 금액은 0 이상이어야 합니다.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("상품 재고는 0 이상이어야 합니다.");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Product create(Long id, String name, int price, int stock) {
        return new Product(id, name, price, stock);
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
