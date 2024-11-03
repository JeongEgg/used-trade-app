package com.example.used_trade_app_backend.oauth.controller;

import com.example.used_trade_app_backend.oauth.utils.GoogleClient;
import com.example.used_trade_app_backend.oauth.response.GoogleAccountProfileResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);
    private final GoogleClient googleClient;

    @PostMapping("/oauth2/code")
    public void receiveAuthCode(@RequestParam("code") String code) {
        logger.info("Received Authorization Code: {}", code);
        GoogleAccountProfileResponse profile = googleClient.getGoogleAccountProfile(code);
        logger.info("profile email : {}",profile.email());
        logger.info("profile id : {}",profile.id());
        logger.info("profile name : {}",profile.name());
    }
}
