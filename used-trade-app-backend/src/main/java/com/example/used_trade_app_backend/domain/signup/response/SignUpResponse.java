package com.example.used_trade_app_backend.domain.signup.response;

import com.example.used_trade_app_backend.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponse {
    private final int errorCode;
    private String message;
    public SignUpResponse(ErrorCode errorCode) {
        this.errorCode = errorCode.code;
        this.message = errorCode.message;
    }
}
