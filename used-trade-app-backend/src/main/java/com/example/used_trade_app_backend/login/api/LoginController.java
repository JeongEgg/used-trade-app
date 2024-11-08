package com.example.used_trade_app_backend.login.api;

import com.example.used_trade_app_backend.login.entity.UserEntity;
import com.example.used_trade_app_backend.login.request.UserInfoRequest;
import com.example.used_trade_app_backend.login.request.UserRegisterRequest;
import com.example.used_trade_app_backend.login.response.UserRegisterResponse;
import com.example.used_trade_app_backend.login.service.UserService;
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

    private final UserService userService;

    @PostMapping("/social-login") // 클라이언트 요청을 받을 엔드포인트
    public ResponseEntity<UserRegisterResponse> receiveUserInfo(@RequestBody UserRegisterRequest userRequest) {
        UserRegisterResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }
}
