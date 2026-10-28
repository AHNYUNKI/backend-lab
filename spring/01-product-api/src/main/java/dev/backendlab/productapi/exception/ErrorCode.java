package dev.backendlab.productapi.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    ALREADY_EXISTS_PRODUCT_NAME(409, "이미 존재하는 상품명입니다."),
    ALREADY_EXISTS_PRODUCT_ID(409, "이미 존재하는 상품ID입니다."),
    NOT_FOUND_PRODUCT(404, "존재하지 않는 상품입니다."),
    PRODUCT_ID_VALIDATION(400, "유효하지 않은 상품ID입니다."),
    PRODUCT_NAME_VALIDATION(400, "유효하지 않은 상품명입니다."),
    PRODUCT_PRICE_VALIDATION(400, "유효하지 않은 상품 가격입니다."),
    PRODUCT_STOCK_VALIDATION(400, "유효하지 않은 상품 재고입니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ErrorCode(HttpStatus code, String message) {
        this.code = code.value();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
