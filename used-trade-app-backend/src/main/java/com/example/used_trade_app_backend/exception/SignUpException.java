package com.example.used_trade_app_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpException extends RuntimeException{

    private final ErrorCode errorCode;
}
