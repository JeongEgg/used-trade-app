package com.example.usedtradeapp.response;

public class GoogleAccessTokenResponse {
    private String access_token;
    private String expires_in;
    private String token_type;

    public String getAccessToken() {
        return access_token;
    }

    public String getExpiresIn() {
        return expires_in;
    }

    public String getTokenType() {
        return token_type;
    }
}
