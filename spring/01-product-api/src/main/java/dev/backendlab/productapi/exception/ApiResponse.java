package dev.backendlab.productapi.exception;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    private ApiResponse(int code, HttpStatus status, String message, T data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, HttpStatus.OK, "OK", data);
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(200, HttpStatus.OK, "OK", null);
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
