package com.example.used_trade_app_backend.domain.profile.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ProfileFragmentResponse {
    private String nickname;
    private int errorCode;
    private String message;

    public ProfileFragmentResponse(String nickname) {
        this.nickname = nickname;
    }

    // 에러 응답용 생성자
    public ProfileFragmentResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
