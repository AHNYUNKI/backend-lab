package dev.backendlab.inventory;

import dev.backendlab.inventory.shop.Product;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final List<Product> products = new ArrayList<>();

    public Product findByProductId(Long productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    // [리뷰: 선택] product에는 상품 객체가 아니라 ID가 들어옵니다. productId로 바꾸면 호출 의도가 명확해집니다.
    // 단건 조회 이름도 findById로 맞추면 existsById와 같은 기준으로 읽을 수 있습니다.
    // 수정완료
    public boolean existsById(Long product) {
        return findByProductId(product) != null;
    }

    public void createProduct(Long productId, String name, int price, int stock) {

        if (isProductIdNull(productId)) {
            throw new IllegalArgumentException("상품 ID는 null일 수 없습니다.");
        }

        if (existsById(productId)) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        };

        products.add(Product.create(productId, name, price, stock));
    }

    // [리뷰: 선택] List.copyOf로 목록의 추가·삭제는 차단됐지만 Product 참조는 공유됩니다.
    // findAll().get(0).changeStock(9)는 실제 재고를 바꿉니다. 조회를 읽기 전용으로 만들고 싶다면
    // 불변 조회 DTO를 반환하세요. 공개된 findByProductId도 같은 기준으로 검토해야 합니다.
    public List<Product> findAll() {
        return List.copyOf(products);
    }

    public boolean isNotExistsProduct(long productId) {
        return !existsById(productId);
    }

    // [리뷰: 선택] productStockEdit보다 changeStock이 동작을 바로 드러내고 Product의 이름과도 일치합니다.
    // stock은 증감량이 아닌 교체할 수량이므로 newStock으로 이름 붙이면 10 → 7이라는 계약이 분명해집니다.
    // 수정완료
    public void changeStock(Long productId, int newStock) {
        if (isProductIdNull(productId)) {
            throw new IllegalArgumentException("상품 ID는 null일 수 없습니다.");
        }

        if (isNotExistsProduct(productId)) {
            throw new IllegalArgumentException("존재하지 않은 상품입니다.");
        };

        Product product = findByProductId(productId);
        product.changeStock(newStock);
    }

    private boolean isProductIdNull(Long productId) {
        return productId == null;
    }

}
