package com.example.usedtradeapp.domain.profile.response;

public class ProfileFragmentResponse {
    private String nickname;

    public ProfileFragmentResponse(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
