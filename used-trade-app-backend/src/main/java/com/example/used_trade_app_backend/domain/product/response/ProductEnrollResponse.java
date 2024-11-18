package com.example.used_trade_app_backend.domain.product.response;

import com.example.used_trade_app_backend.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductEnrollResponse {
    private String title;  // 상품 제목
    private int errorCode;
    private String message;

    public ProductEnrollResponse(ErrorCode errorCode) {
        this.errorCode = errorCode.code;
        this.message = errorCode.message;
    }

    public ProductEnrollResponse(String title, ErrorCode errorCode) {
        this.title = title;
        this.errorCode = errorCode.code;
        this.message = errorCode.message;
    }
}