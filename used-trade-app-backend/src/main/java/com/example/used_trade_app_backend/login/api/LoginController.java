package com.example.used_trade_app_backend.login.api;

import com.example.used_trade_app_backend.login.request.UserInfoRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/user/info") // 클라이언트 요청을 받을 엔드포인트
    public ResponseEntity<Void> receiveUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        // 로그로 사용자 정보 출력
        logger.info("Received User Info: UserID = {}, Name = {}, Email = {}",
                userInfoRequest.getUserId(), userInfoRequest.getName(), userInfoRequest.getEmail());

        // 성공적으로 처리했음을 나타내는 200 OK 응답 반환
        return ResponseEntity.ok().build();
    }
}
