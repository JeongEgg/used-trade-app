package com.example.used_trade_app_backend.domain.profile.api;

import com.example.used_trade_app_backend.domain.profile.request.ProfileUpdateRequest;
import com.example.used_trade_app_backend.domain.profile.response.ProfileActivityResponse;
import com.example.used_trade_app_backend.domain.profile.response.ProfileDeleteResponse;
import com.example.used_trade_app_backend.domain.profile.response.ProfileFragmentResponse;
import com.example.used_trade_app_backend.domain.profile.response.ProfileUpdateResponse;
import com.example.used_trade_app_backend.domain.profile.service.ProfileService;
import com.example.used_trade_app_backend.exception.ErrorCode;
import com.example.used_trade_app_backend.exception.ProfileNotFoundException;
import com.example.used_trade_app_backend.exception.UserInformationTamperedException;
import com.example.used_trade_app_backend.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
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
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/profile/activity")
    public ResponseEntity<ProfileActivityResponse> getUserProfile(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        try {
            // JWT에서 데이터 추출
            String username = JwtUtil.extractUsername(token);
            String userId = JwtUtil.extractUserId(token);

            String nickname = profileService.getNicknameByUserId(userId);

            logger.info("username : {}",username);
            logger.info("userId : {}",userId);
            logger.info("nickname : {}", nickname);

            return ResponseEntity.ok(new ProfileActivityResponse(userId,username,nickname));
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/profile/activity")
    public ResponseEntity<ProfileUpdateResponse> updateProfile(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ProfileUpdateRequest profileUpdateRequest){
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            String username = JwtUtil.extractUsername(token);
            String userId = JwtUtil.extractUserId(token);

            profileService.updateProfile(userId, username, profileUpdateRequest.getNickname(), profileUpdateRequest.getUsername());

            String newToken = JwtUtil.generateTokenByLogin(userId, profileUpdateRequest.getUsername());

            ProfileUpdateResponse response = new ProfileUpdateResponse(ErrorCode.SUCCESS.code, "프로필이 성공적으로 업데이트되었습니다.");
            response.setUsername(profileUpdateRequest.getUsername());
            response.setNickname(profileUpdateRequest.getNickname());
            response.setToken(newToken);

            return ResponseEntity.ok(response);
        } catch (UserInformationTamperedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ProfileUpdateResponse(e.getErrorCode().code, e.getErrorCode().message));
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProfileUpdateResponse(e.getErrorCode().code, e.getErrorCode().message));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProfileUpdateResponse(ErrorCode.INTERNAL_SERVER_ERROR.code, ErrorCode.INTERNAL_SERVER_ERROR.message));
        }
    }

    @DeleteMapping("/profile")
    public ResponseEntity<ProfileDeleteResponse> deleteProfile(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            String userId = JwtUtil.extractUserId(token);

            logger.info("계정 삭제 요청 userId : {}",userId);

            profileService.deleteProfile(userId);

            return ResponseEntity.ok(new ProfileDeleteResponse(200, "계정이 성공적으로 삭제되었습니다."));
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProfileDeleteResponse(e.getErrorCode().code, e.getErrorCode().message));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProfileDeleteResponse(ErrorCode.INTERNAL_SERVER_ERROR.code, ErrorCode.INTERNAL_SERVER_ERROR.message));
        }
    }
}
