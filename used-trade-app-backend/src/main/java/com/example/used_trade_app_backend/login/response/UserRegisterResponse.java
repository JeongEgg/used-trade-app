package com.example.used_trade_app_backend.login.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterResponse {
    private int id;
    private String socialId;
    private String username;
    private String nickname;
}
