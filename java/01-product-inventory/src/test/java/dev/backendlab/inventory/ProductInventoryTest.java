package dev.backendlab.inventory;

import dev.backendlab.inventory.shop.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

// [리뷰: 필수] 현재 두 테스트는 생성 시 음수 거부만 확인하고 정상 등록·조회·재고 변경을 검증하지 않습니다.
// InventoryTest에 빈 목록, 두 상품 등록 후 값 확인, 10 → 7 교체, 다른 상품 불변을 직접 검증하는 테스트를 추가하세요.
// 중복 ID·없는 ID·음수 재고 변경을 거절한 뒤 기존 데이터가 유지되는지도 확인하면 리팩터링 회귀를 잡을 수 있습니다.
// [리뷰: 선택] 현재 클래스는 Product만 검증하므로 ProductTest라는 이름이 실제 범위를 더 정확히 드러냅니다.
class ProductInventoryTest {

    @Test
    @DisplayName("음수 가격으로 Product.create(...)를 호출하면 예외가 난다.")
    void createProductWithNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Product.create(1L, "상품1", -100, 10));
    }

    @Test
    @DisplayName("음수 재고로 Product.create(...)를 호출하면 예외가 난다.")
    void createProductWithNegativeStockThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Product.create(1L, "상품1", 100, -10));
    }

}
