package com.example.usedtradeapp.oauth.response;

public class UserRegisterResponse {

    private String token;  // 이제 token만 반환

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
