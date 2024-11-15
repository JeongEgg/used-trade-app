package com.example.used_trade_app_backend.domain.login.request;

import lombok.Data;

@Data
public class UserInfoRequest {
    private String userId;
    private String name;
    private String email;
}
