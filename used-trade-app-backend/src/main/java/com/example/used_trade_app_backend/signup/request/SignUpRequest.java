package com.example.used_trade_app_backend.signup.request;

import com.example.used_trade_app_backend.signup.entity.UserEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(this.email)
                .username(this.name)
                .password(this.password)
                .nickname(generateRandomNickname())
                .build();
    }

    private String generateRandomNickname() {
        return "User_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
