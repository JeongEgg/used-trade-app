package com.example.used_trade_app_backend.oauth.utils;

import com.example.used_trade_app_backend.oauth.exception.LoginException;
import com.example.used_trade_app_backend.oauth.request.GoogleAccessTokenRequest;
import com.example.used_trade_app_backend.oauth.response.GoogleAccessTokenResponse;
import com.example.used_trade_app_backend.oauth.response.GoogleAccountProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.example.used_trade_app_backend.oauth.exception.LoginExceptionType.NOT_FOUND_GOOGLE_ACCESS_TOKEN_RESPONSE;

@Component
@RequiredArgsConstructor
public class GoogleClient {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.google.authorization-grant-type}")
    private String authorizationCode;
    @Value("${url.access-token}")
    private String accessTokenUrl;
    @Value("${url.profile}")
    private String profileUrl;


    private final RestTemplate restTemplate;


    public GoogleAccountProfileResponse getGoogleAccountProfile(final String code) {
        // 안드로이드 클라이언트로부터 받은 code를 구글로 전송할 access-token으로 변환
        final String accessToken = requestGoogleAccessToken(code);
        // 구글 서버에 access-token을 전송하여 소셜 로그인 계정에 대한 정보(profile)를 받음
        return requestGoogleAccountProfile(accessToken);
    }

    private String requestGoogleAccessToken(final String code) {
        final String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<GoogleAccessTokenRequest> httpEntity = new HttpEntity<>(
                new GoogleAccessTokenRequest(decodedCode, clientId, clientSecret, redirectUri, authorizationCode),
                headers
        );
        final GoogleAccessTokenResponse response = restTemplate.exchange(
                accessTokenUrl, HttpMethod.POST, httpEntity, GoogleAccessTokenResponse.class
        ).getBody();
        return Optional.ofNullable(response)
                .orElseThrow(() -> new LoginException(NOT_FOUND_GOOGLE_ACCESS_TOKEN_RESPONSE))
                .accessToken();
    }

    private GoogleAccountProfileResponse requestGoogleAccountProfile(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        final HttpEntity<GoogleAccessTokenRequest> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(profileUrl, HttpMethod.GET, httpEntity, GoogleAccountProfileResponse.class)
                .getBody();
    }
}

