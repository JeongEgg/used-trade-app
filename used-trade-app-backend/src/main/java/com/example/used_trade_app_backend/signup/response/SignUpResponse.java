package com.example.used_trade_app_backend.signup.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponse {
    private final int errorCode;
    private String message;
}
