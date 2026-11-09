package dev.backendlab.goldguard.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String code,
        String message
) {
}
