package com.example.used_trade_app_backend.domain.signup.service;

import com.example.used_trade_app_backend.exception.ErrorCode;
import com.example.used_trade_app_backend.db.entity.UserEntity;
import com.example.used_trade_app_backend.db.repository.UserRepository;
import com.example.used_trade_app_backend.domain.signup.request.SignUpRequest;
import com.example.used_trade_app_backend.domain.signup.response.SignUpResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public SignUpResponse signup(SignUpRequest signUpRequest) {
        UserEntity user = signUpRequest.toEntity();

        Optional<UserEntity> existingUser = userRepository.findByUserId(user.getUserId());
        if (existingUser.isPresent()) {
            return new SignUpResponse(ErrorCode.DUPLICATED_USER_ID);
        }
        String password = signUpRequest.getPassword();
        if (password.length() < 8) {
            return new SignUpResponse(ErrorCode.INVALID_PASSWORD_LENGTH);
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*()].*")) {
            return new SignUpResponse(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
        userRepository.save(user);
        return new SignUpResponse(ErrorCode.SUCCESS);
    }
}
