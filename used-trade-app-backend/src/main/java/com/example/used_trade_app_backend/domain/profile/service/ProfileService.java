package com.example.used_trade_app_backend.domain.profile.service;

import com.example.used_trade_app_backend.db.entity.UserEntity;
import com.example.used_trade_app_backend.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public String getNicknameByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .map(UserEntity::getNickname)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for userId: " + userId));
    }
}
