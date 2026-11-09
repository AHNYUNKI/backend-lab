package dev.backendlab.goldguard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    REWARD_ALREADY_CLAIMED("이미 보상을 수령했습니다.");

    private final String message;

}