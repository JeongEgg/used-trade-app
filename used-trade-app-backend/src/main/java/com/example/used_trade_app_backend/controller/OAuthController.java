package com.example.used_trade_app_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    @PostMapping("/oauth2/code")
    public void receiveAuthCode(@RequestParam("code") String code) {
        // 받은 인가 코드를 로그로 출력
        logger.info("Received Authorization Code: {}", code);
    }
}
