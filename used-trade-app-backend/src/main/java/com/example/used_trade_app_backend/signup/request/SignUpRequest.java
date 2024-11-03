package com.example.used_trade_app_backend.signup.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
}
