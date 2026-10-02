package dev.backendlab.inventory.shop;

public class Product {

    private Long id;

    private String name;

    private int price;

    private int quantity;

    private Product(Long id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product create(Long id, String name, int price, int quantity) {
        if(price < 0) {
            throw new IllegalArgumentException("상품 가격은 양수여야합니다.");
        }

        if(quantity < 0) {
            throw new IllegalArgumentException("상품 재고는 양수여야합니다.");
        }

        return new Product(id, name, price, quantity);
    }

    public void changeStock(int changeStock) {
        if(changeStock < 0) {
            throw new IllegalArgumentException("재고는 0보다 작을 수 없습니다.");
        }
        this.quantity = changeStock;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.quantity;
    }

}
