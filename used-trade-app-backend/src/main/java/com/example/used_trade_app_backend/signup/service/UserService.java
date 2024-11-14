package com.example.used_trade_app_backend.signup.service;

import com.example.used_trade_app_backend.exception.ErrorCode;
import com.example.used_trade_app_backend.exception.SignUpException;
import com.example.used_trade_app_backend.signup.entity.UserEntity;
import com.example.used_trade_app_backend.signup.repository.UserRepository;
import com.example.used_trade_app_backend.signup.request.SignUpRequest;
import com.example.used_trade_app_backend.signup.response.SignUpResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public static final int ERROR_CODE_DUPLICATED_USER_ID = 1001;
    public static final int ERROR_CODE_INVALID_PASSWORD = 1003;
    public static final int ERROR_CODE_SUCCESS = 0;

    public SignUpResponse signup(SignUpRequest signUpRequest) {
        UserEntity user = signUpRequest.toEntity();

        Optional<UserEntity> existingUser = userRepository.findByUserId(user.getUserId());
        if (existingUser.isPresent()) {
            return new SignUpResponse(ERROR_CODE_DUPLICATED_USER_ID, "이미 존재하는 아이디입니다.");
        }
        String password = signUpRequest.getPassword();
        if (password.length() < 8) {
            return new SignUpResponse(ERROR_CODE_INVALID_PASSWORD, "비밀번호는 8자리 이상이어야 합니다.");
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*()].*")) {
            return new SignUpResponse(ERROR_CODE_INVALID_PASSWORD, "비밀번호는 대문자, 소문자, 숫자, 특수 문자가 모두 포함되어야 합니다.");
        }
        userRepository.save(user);
        return new SignUpResponse(ERROR_CODE_SUCCESS, "회원가입이 성공적으로 완료되었습니다.");
    }
}
