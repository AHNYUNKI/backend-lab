package dev.backendlab.productapi.api.request;

import dev.backendlab.productapi.exception.BusinessException;
import dev.backendlab.productapi.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static dev.backendlab.productapi.exception.ErrorCode.*;

public class ProductCreateRequest {

    @NotNull(message = "ID는 필수입니다.")
    private final Long id;

    @NotNull(message = "상품명은 필수입니다.")
    @Size(min = 1, max = 400)
    private final String name;

    @NotNull(message = "가격은 필수입니다.")
    private final int price;

    @NotNull(message = "재고는 필수입니다.")
    private final int stock;

    public ProductCreateRequest(Long id, String name, int price, int stock) {
        productValidity(id, name, price, stock);
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

    private void productValidity(Long id, String name, int price, int stock) {
        if (id == null) {
            throw new BusinessException(PRODUCT_ID_VALIDATION);
        }
        if (id < 0) {
            throw new BusinessException(ErrorCode.PRODUCT_ID_VALIDATION);
        }
        if (name == null) {
            throw new BusinessException(ErrorCode.PRODUCT_NAME_VALIDATION);
        }
        if (name.isEmpty() || name.length() > 400) {
            throw new BusinessException(ErrorCode.PRODUCT_NAME_VALIDATION);
        }
        if (price < 0) {
            throw new BusinessException(ErrorCode.PRODUCT_PRICE_VALIDATION);
        }
        if (stock < 0) {
            throw new BusinessException(ErrorCode.PRODUCT_STOCK_VALIDATION);
        }
    }
}
