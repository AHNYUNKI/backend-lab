package dev.backendlab.productapi.application;

import dev.backendlab.productapi.api.request.ProductCreateRequest;
import dev.backendlab.productapi.application.response.ProductResponse;
import dev.backendlab.productapi.domain.repository.ProductRepository;
import dev.backendlab.productapi.exception.BusinessException;
import dev.backendlab.productapi.exception.ErrorCode;
import org.assertj.core.internal.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void 상품을_등록할_수_있다() {
        // given
        ProductCreateRequest request = ProductCreateRequest.of(1L, "test", 1000, 10);

        // when
        ProductResponse response = productService.createProduct(request);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test", response.getName());
        assertEquals(1000, response.getPrice());
        assertEquals(10, response.getStock());
    }

    @Test
    void 상품을_등록할_떄_ID가_중복되면_예외가_발생한다() {
        // given
        ProductCreateRequest firstRequest = ProductCreateRequest.of(1L, "test", 1000, 10);
        productService.createProduct(firstRequest);

        ProductCreateRequest secondRequest = ProductCreateRequest.of(1L, "test2", 1000, 10);

        // when then
        assertThatThrownBy(() -> productService.createProduct(secondRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.ALREADY_EXISTS_PRODUCT_ID.getMessage());
        assertThat(productRepository.existsByName(secondRequest.getName())).isFalse();
    }

    @Test
    void ID가_달라도_상품명이_같으면_등록을_거절한다() {
        // given
        ProductCreateRequest firstRequest = ProductCreateRequest.of(1L, "test", 1000, 10);
        productService.createProduct(firstRequest);

        ProductCreateRequest secondRequest = ProductCreateRequest.of(2L, "test", 1000, 10);

        // when then
        assertThatThrownBy(() -> productService.createProduct(secondRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.ALREADY_EXISTS_PRODUCT_NAME.getMessage());
        assertThat(productRepository.existsById(secondRequest.getId())).isFalse();
    }

    @Test
    void 상품_ID로_단건_조회할_수_있다() {
        // given
        ProductCreateRequest request = ProductCreateRequest.of(1L, "조회용 상품", 1000, 10);
        productService.createProduct(request);

        // when
        ProductResponse response = productService.findProduct(request.getId());

        // then
        assertThat(response.getId()).isEqualTo(request.getId());
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getPrice()).isEqualTo(request.getPrice());
        assertThat(response.getStock()).isEqualTo(request.getStock());
    }

    @Test
    void 존재하지않은_상품_ID로_조회하면_에러가_발생한다() {
        // when // then
        assertThatThrownBy(() -> productService.findProduct(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.NOT_FOUND_PRODUCT.getMessage());
    }

}