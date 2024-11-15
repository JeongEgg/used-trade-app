package com.example.used_trade_app_backend.db.repository;

import com.example.used_trade_app_backend.db.entity.UserSocialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSocialRepository extends JpaRepository<UserSocialEntity, Integer> {
    Optional<UserSocialEntity> findBySocialIdAndUsername(String socialId, String username);
}
