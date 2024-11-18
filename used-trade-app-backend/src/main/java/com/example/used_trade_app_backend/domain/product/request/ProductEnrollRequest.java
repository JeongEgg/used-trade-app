package com.example.used_trade_app_backend.domain.product.request;

import com.example.used_trade_app_backend.domain.product.dto.ProductEnrollDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductEnrollRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private Set<Long> categoryIds;

    public ProductEnrollDto toDto(String userId) {
        return new ProductEnrollDto(userId, this.title, this.description, this.price, this.categoryIds);
    }
}
