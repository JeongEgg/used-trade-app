package com.example.used_trade_app_backend.domain.profile.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequest {
    private String username;
    private String nickname;
}
