package dev.backendlab.productapi.api.request;

import dev.backendlab.productapi.exception.BusinessException;
import dev.backendlab.productapi.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import static dev.backendlab.productapi.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;

class ProductCreateRequestTest {

    @Test
    void 클라이언트에서_상품명을_누락하면_예외가_발생한다() {
        // given

        // when // then
        assertThatThrownBy(() -> ProductCreateRequest.of(1L, null, 1000, 10))
                .isInstanceOf(BusinessException.class)
                .hasMessage(PRODUCT_NAME_VALIDATION.getMessage());
    }

    @Test
    void 클라이언트에서_상품_가격을_음수로_입력하면_예외가_발생한다() {
        // given

        // when // then
        assertThatThrownBy(() -> ProductCreateRequest.of(1L, "상품명", -1000, 10))
                .isInstanceOf(BusinessException.class)
                .hasMessage(PRODUCT_PRICE_VALIDATION.getMessage());

    }

    @Test
    void 클라이언트에서_상품_수량을_음수로_입력하면_예외가_발생한다() {
        // given


        // when // then
        assertThatThrownBy(() -> ProductCreateRequest.of(1L, "상품명", 1000, -10))
                .isInstanceOf(BusinessException.class)
                .hasMessage(PRODUCT_STOCK_VALIDATION.getMessage());

    }

}