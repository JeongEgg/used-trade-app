package com.example.used_trade_app_backend.login.repository;

import com.example.used_trade_app_backend.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findBySocialIdAndUsername(String socialId, String username);
}
