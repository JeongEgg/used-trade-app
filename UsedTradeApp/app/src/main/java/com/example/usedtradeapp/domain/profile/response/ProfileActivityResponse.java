package com.example.usedtradeapp.domain.profile.response;

public class ProfileActivityResponse {
    private String email;
    private String username;
    private String nickname;

    public ProfileActivityResponse(String email, String username, String nickname) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
