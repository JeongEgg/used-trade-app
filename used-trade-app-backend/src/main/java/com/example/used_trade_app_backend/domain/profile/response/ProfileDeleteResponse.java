package com.example.used_trade_app_backend.domain.profile.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDeleteResponse {
    private int code;
    private String message;
}
