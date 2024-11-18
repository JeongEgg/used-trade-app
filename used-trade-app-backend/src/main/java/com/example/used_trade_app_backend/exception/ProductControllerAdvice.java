package com.example.used_trade_app_backend.exception;

import com.example.used_trade_app_backend.domain.product.response.ProductEnrollResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ProductEnrollResponse> handleProductException(ProductException exception) {
        ProductEnrollResponse response = new ProductEnrollResponse(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
