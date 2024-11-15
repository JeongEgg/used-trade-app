package com.example.used_trade_app_backend.domain.login.api;

import com.example.used_trade_app_backend.exception.ErrorCode;
import com.example.used_trade_app_backend.domain.login.request.UserLoginRequest;
import com.example.used_trade_app_backend.domain.login.request.UserRegisterRequest;
import com.example.used_trade_app_backend.domain.login.response.UserLoginResponse;
import com.example.used_trade_app_backend.domain.login.response.UserRegisterResponse;
import com.example.used_trade_app_backend.domain.login.service.UserLoginService;
import com.example.used_trade_app_backend.domain.login.service.UserSocialService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    private final UserLoginService userLoginService;

    @PostMapping("/social-login") // 클라이언트 요청을 받을 엔드포인트
    public ResponseEntity<UserRegisterResponse> receiveUserInfo(@RequestBody UserRegisterRequest userRequest) {
        String token = userSocialService.createUser(userRequest);
        return ResponseEntity.ok(new UserRegisterResponse(token)); // token만 반환
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> generalLogin(@RequestBody UserLoginRequest userLoginRequest){
        // 로그인 처리 후 LoginErrorResponse 반환
        UserLoginResponse response = userLoginService.userLogin(userLoginRequest);

        if (response.getErrorCode() != ErrorCode.SUCCESS_LOGIN.code) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }
}
