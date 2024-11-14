package com.example.used_trade_app_backend.login.response;

import com.example.used_trade_app_backend.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private final int errorCode;
    private String message;
    private String token;

    public UserLoginResponse(ErrorCode errorCode) {
        this.errorCode = errorCode.code;
        this.message = errorCode.message;
    }

    public UserLoginResponse(ErrorCode errorCode, String token) {
        this.errorCode = errorCode.code;
        this.message = errorCode.message;
        this.token = token;
    }
}
