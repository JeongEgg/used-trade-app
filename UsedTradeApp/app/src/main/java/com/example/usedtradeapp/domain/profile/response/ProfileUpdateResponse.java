package com.example.usedtradeapp.domain.profile.response;

public class ProfileUpdateResponse {
    private String username;
    private String nickname;
    private String token;

    public ProfileUpdateResponse(String username, String nickname, String token) {
        this.username = username;
        this.nickname = nickname;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
