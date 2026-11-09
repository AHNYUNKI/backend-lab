package dev.backendlab.goldguard.api;

import dev.backendlab.goldguard.exception.BusinessException;
import dev.backendlab.goldguard.exception.ErrorCode;
import dev.backendlab.goldguard.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {

        HttpStatus status = switchHttpStatus(e.getErrorCode());

        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .code(e.getErrorCode().name())
                        .message(e.getMessage())
                        .build());
    }

    @NonNull
    private static HttpStatus switchHttpStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case REWARD_ALREADY_CLAIMED -> HttpStatus.CONFLICT;
        };
    }
}
