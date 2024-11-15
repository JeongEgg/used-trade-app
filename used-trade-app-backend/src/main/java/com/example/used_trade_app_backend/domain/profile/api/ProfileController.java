package com.example.used_trade_app_backend.domain.profile.api;

import com.example.used_trade_app_backend.domain.profile.response.ProfileFragmentResponse;
import com.example.used_trade_app_backend.domain.profile.service.ProfileService;
import com.example.used_trade_app_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileService profileService;
    @GetMapping("/profile/fragment")
    public ResponseEntity<ProfileFragmentResponse> getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        // JWT 토큰에서 "Bearer " 접두사를 제거
        String token = authorizationHeader.replace("Bearer ", "");

        try {
            // JWT에서 데이터 추출
            String username = JwtUtil.extractUsername(token);
            String userId = JwtUtil.extractUserId(token);

            String nickname = profileService.getNicknameByUserId(userId);

            logger.info("username : {}",username);
            logger.info("userId : {}",userId);
            logger.info("nickname : {}", nickname);

            return ResponseEntity.ok(new ProfileFragmentResponse(nickname));
        } catch (UsernameNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProfileFragmentResponse("User not found"));
        } catch (Exception e) {
            logger.error("Error processing request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ProfileFragmentResponse("Invalid token"));
        }
    }
}
