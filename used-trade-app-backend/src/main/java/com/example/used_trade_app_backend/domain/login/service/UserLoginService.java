package com.example.used_trade_app_backend.domain.login.service;

import com.example.used_trade_app_backend.exception.ErrorCode;
import com.example.used_trade_app_backend.domain.login.request.UserLoginRequest;
import com.example.used_trade_app_backend.domain.login.response.UserLoginResponse;
import com.example.used_trade_app_backend.domain.login.utils.JwtUtil;
import com.example.used_trade_app_backend.db.entity.UserEntity;
import com.example.used_trade_app_backend.db.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserLoginService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserLoginResponse userLogin(UserLoginRequest userLoginRequest) {
        // 사용자 조회
        UserEntity userEntity = userRepository.findByUserId(userLoginRequest.getEmail())
                .orElse(new UserEntity()); // 사용자 정보가 없으면 빈 객체 반환

        if (userEntity.getUserId() == null) {
            // 사용자 없으면 에러 응답 반환
            return new UserLoginResponse(ErrorCode.USER_NOT_FOUND);
        }

        // 비밀번호 일치 여부 확인
        if (!userEntity.getPassword().equals(userLoginRequest.getPassword())) {
            // 비밀번호가 일치하지 않으면 에러 응답 반환
            return new UserLoginResponse(ErrorCode.INVALID_PASSWORD);
        }

        // JWT 토큰 생성
        String token = jwtUtil.generateTokenByLogin(userEntity.getUserId(), userEntity.getUsername());
        // 성공 시에는 null이나 다른 형태로 응답, 에러가 없으면 token만 반환
        return new UserLoginResponse(ErrorCode.SUCCESS_LOGIN, token);
    }
}
