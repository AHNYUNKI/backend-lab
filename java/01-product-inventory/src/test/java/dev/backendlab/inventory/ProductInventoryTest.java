package dev.backendlab.inventory;

import dev.backendlab.inventory.shop.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
