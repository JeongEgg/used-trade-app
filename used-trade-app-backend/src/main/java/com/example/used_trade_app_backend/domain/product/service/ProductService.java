package com.example.used_trade_app_backend.domain.product.service;

import com.example.used_trade_app_backend.db.entity.CategoryEntity;
import com.example.used_trade_app_backend.db.entity.ProductInfoEntity;
import com.example.used_trade_app_backend.db.repository.CategoryRepository;
import com.example.used_trade_app_backend.db.repository.ProductInfoRepository;
import com.example.used_trade_app_backend.domain.product.dto.ProductEnrollDto;
import com.example.used_trade_app_backend.domain.product.request.ProductEnrollRequest;
import com.example.used_trade_app_backend.exception.ErrorCode;
import com.example.used_trade_app_backend.exception.ProductException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductInfoRepository productInfoRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductInfoEntity createProduct(ProductEnrollDto productEnrollDto) {
        if (productInfoRepository.existsByTitle(productEnrollDto.getTitle())) {
            throw new ProductException(ErrorCode.DUPLICATE_PRODUCT_TITLE);
        }

        Set<CategoryEntity> categories = new HashSet<>();
        for (Long categoryId : productEnrollDto.getCategoryIds()) {
            CategoryEntity category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ProductException(ErrorCode.CATEGORY_NOT_FOUND));
            categories.add(category);
        }

        if (categories.isEmpty()) {
            throw new ProductException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        ProductInfoEntity productInfo = productEnrollDto.toEntity(categories);

        return productInfoRepository.save(productInfo);
    }
}
