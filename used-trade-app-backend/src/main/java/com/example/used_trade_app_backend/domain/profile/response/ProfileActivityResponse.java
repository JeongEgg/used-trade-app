package com.example.used_trade_app_backend.domain.profile.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileActivityResponse {
    private String email;
    private String username;
    private String nickname;
    private int errorCode;
    private String message;

    public ProfileActivityResponse(String email, String username, String nickname) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
    }

    public ProfileActivityResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
