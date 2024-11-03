package com.example.used_trade_app_backend.oauth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleAccessTokenResponse (
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_in")
        Integer expiresIn,
        @JsonProperty("scope")
        String scope,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("id_token")
        String idToken
){}
