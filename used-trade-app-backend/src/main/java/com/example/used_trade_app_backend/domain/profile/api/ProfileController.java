package com.example.used_trade_app_backend.domain.profile.api;

import com.example.used_trade_app_backend.domain.profile.response.ProfileFragmentResponse;
import com.example.used_trade_app_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping("/profile/fragment")
    public ResponseEntity<ProfileFragmentResponse> getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        // JWT 토큰에서 "Bearer " 접두사를 제거
        String token = authorizationHeader.replace("Bearer ", "");

        try {
            // JWT에서 데이터 추출
            String username = JwtUtil.extractUsername(token);
            String userId = JwtUtil.extractUserId(token);

            // 결과 반환
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("username", username);
            userInfo.put("userId", userId);

            logger.info("username : {}",username);
            logger.info("userId : {}",userId);

            return ResponseEntity.ok(new ProfileFragmentResponse(""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ProfileFragmentResponse(""));
        }
    }
}
