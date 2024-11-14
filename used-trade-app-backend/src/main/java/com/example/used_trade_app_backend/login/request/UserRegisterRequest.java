package com.example.used_trade_app_backend.login.request;

import com.example.used_trade_app_backend.login.entity.UserSocialEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserRegisterRequest {
    private String userId;
    private String name;
    private String email;

    public UserSocialEntity toEntity() {
        return UserSocialEntity.builder()
                .socialId(this.email)
                .username(this.name)
                .nickname(generateRandomNickname())  // Generates a random nickname
                .build();
    }

    private String generateRandomNickname() {
        return "User_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
