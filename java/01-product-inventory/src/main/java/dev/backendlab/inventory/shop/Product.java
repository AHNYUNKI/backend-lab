package dev.backendlab.inventory.shop;

public class Product {

    private Long id;

    private String name;

    private int price;

    private int stock;

    private Product(Long id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // [리뷰: 선택] Inventory는 null ID를 막지만 이 공개 팩터리는 null ID·이름도 가진 Product를 만듭니다.
    // Product 자체가 항상 유효해야 한다는 규칙을 둘 경우 ID·이름 검증도 생성 시점에 모아 주세요.
    // 콘솔의 빠른 안내와 객체의 최종 검증은 목적이 다르므로 둘 다 둘 수 있습니다.
    public static Product create(Long id, String name, int price, int quantity) {
        if(price < 0) {
            throw new IllegalArgumentException("상품 가격은 양수여야합니다.");
        }

        if(quantity < 0) {
            throw new IllegalArgumentException("상품 재고는 양수여야합니다.");
        }

        return new Product(id, name, price, quantity);
    }

    // [리뷰: 선택] changeStock이라는 인자명은 동작인지 증감량인지 모호합니다. newStock이면 교체할 값임이 분명합니다.
    // 같은 개념인 필드 quantity와 getStock의 용어도 stock으로 통일하면 상태를 따라가기 쉽습니다.
    // 수정 완료
    public void changeStock(int newStock) {
        if(newStock < 0) {
            throw new IllegalArgumentException("재고는 0보다 작을 수 없습니다.");
        }
        this.stock = newStock;
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
        return this.stock;
    }

}
