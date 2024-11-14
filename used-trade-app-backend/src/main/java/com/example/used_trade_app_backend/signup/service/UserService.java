package com.example.used_trade_app_backend.signup.service;

import com.example.used_trade_app_backend.signup.entity.UserEntity;
import com.example.used_trade_app_backend.signup.repository.UserRepository;
import com.example.used_trade_app_backend.signup.request.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String signup(SignUpRequest signUpRequest) {
        UserEntity user = signUpRequest.toEntity();
        Optional<UserEntity> existingUser = userRepository.findByUserId(user.getUserId());
        if (existingUser.isPresent()) {
            return "이미 존재하는 아이디입니다.";
        }
        userRepository.save(user);
        return "회원가입이 성공적으로 완료되었습니다.";
    }
}
