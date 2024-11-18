package com.example.used_trade_app_backend.domain.product.dto;

import com.example.used_trade_app_backend.db.entity.CategoryEntity;
import com.example.used_trade_app_backend.db.entity.ProductInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
public class ProductEnrollDto {
    private String userId;
    private String title;
    private String description;
    private BigDecimal price;
    private Set<Long> categoryIds;

    public ProductInfoEntity toEntity(Set<CategoryEntity> categories) {
        return ProductInfoEntity.builder()
                .userId(this.userId)
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .categories(categories)
                .status("판매 중")
                .build();
    }
}
