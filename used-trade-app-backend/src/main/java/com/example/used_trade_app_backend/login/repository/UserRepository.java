package com.example.used_trade_app_backend.login.repository;

import com.example.used_trade_app_backend.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
