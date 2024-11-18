package com.example.used_trade_app_backend.domain.profile.service;

import com.example.used_trade_app_backend.db.entity.UserEntity;
import com.example.used_trade_app_backend.db.repository.UserRepository;
import com.example.used_trade_app_backend.exception.ErrorCode;
import com.example.used_trade_app_backend.exception.ProfileNotFoundException;
import com.example.used_trade_app_backend.exception.UserInformationTamperedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public String getNicknameByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .map(UserEntity::getNickname)
                .orElseThrow(() -> new ProfileNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public void updateProfile(String userId, String username, String newNickname, String newUsername) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException(ErrorCode.USER_NOT_FOUND));
        if (!userEntity.getUsername().equals(username)) {
            throw new UserInformationTamperedException(ErrorCode.USER_INFORMATION_TAMPERED);
        }

        userEntity.setNickname(newNickname);
        userEntity.setUsername(newUsername);

        userRepository.save(userEntity);
    }

    public void deleteProfile(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(userEntity);
    }
}
