package com.example.used_trade_app_backend.db.repository;

import com.example.used_trade_app_backend.db.entity.ProductInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, Long> {
    boolean existsByTitle(String title);
}
