package com.example.used_trade_app_backend.login.service;

import com.example.used_trade_app_backend.login.entity.UserEntity;
import com.example.used_trade_app_backend.login.repository.UserRepository;
import com.example.used_trade_app_backend.login.request.UserRegisterRequest;
import com.example.used_trade_app_backend.login.response.UserRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserRegisterResponse createUser(UserRegisterRequest userRequest) {
        UserEntity user = userRequest.toEntity();
        Optional<UserEntity> existingUser = userRepository.findBySocialIdAndUsername(user.getSocialId(), user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("동일한 이메일로 계정이 이미 존재합니다.");
        }
        user = userRepository.save(user);
        return new UserRegisterResponse(user.getId(), user.getSocialId(), user.getUsername(), user.getNickname());
    }
}
