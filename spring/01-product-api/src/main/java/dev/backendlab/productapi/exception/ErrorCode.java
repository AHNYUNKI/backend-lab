package dev.backendlab.productapi.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    ALREADY_EXISTS_PRODUCT_NAME(409, "이미 존재하는 상품명입니다."),
    ALREADY_EXISTS_PRODUCT_ID(409, "이미 존재하는 상품ID입니다.");

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
