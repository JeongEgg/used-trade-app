package com.example.used_trade_app_backend.login.api;

import com.example.used_trade_app_backend.login.request.UserRegisterRequest;
import com.example.used_trade_app_backend.login.response.UserRegisterResponse;
import com.example.used_trade_app_backend.login.service.UserSocialService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserSocialService userSocialService;

    @PostMapping("/social-login") // 클라이언트 요청을 받을 엔드포인트
    public ResponseEntity<UserRegisterResponse> receiveUserInfo(@RequestBody UserRegisterRequest userRequest) {
        String token = userSocialService.createUser(userRequest);
        return ResponseEntity.ok(new UserRegisterResponse(token)); // token만 반환
    }
}
