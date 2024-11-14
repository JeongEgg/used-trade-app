package com.example.used_trade_app_backend.login.service;

import com.example.used_trade_app_backend.login.entity.UserSocialEntity;
import com.example.used_trade_app_backend.login.repository.UserSocialRepository;
import com.example.used_trade_app_backend.login.request.UserRegisterRequest;
import com.example.used_trade_app_backend.login.response.UserRegisterResponse;
import com.example.used_trade_app_backend.login.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSocialService {
    private final UserSocialRepository userSocialRepository;

    @Transactional
    public String createUser(UserRegisterRequest userRequest) {
        UserSocialEntity user = userRequest.toEntity();
        Optional<UserSocialEntity> existingUser = userSocialRepository.findBySocialIdAndUsername(user.getSocialId(), user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("동일한 이메일로 계정이 이미 존재합니다.");
        }
        user = userSocialRepository.save(user);
        String token = JwtUtil.generateTokenBySocialLogin(user.getSocialId(), user.getUsername());

        return token;
    }
}
