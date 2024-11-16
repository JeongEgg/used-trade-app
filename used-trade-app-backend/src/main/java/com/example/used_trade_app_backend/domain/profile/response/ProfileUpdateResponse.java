package com.example.used_trade_app_backend.domain.profile.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateResponse {
    private String username;
    private String nickname;
    private String token;
    private int errorCode;
    private String message;

    public ProfileUpdateResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
