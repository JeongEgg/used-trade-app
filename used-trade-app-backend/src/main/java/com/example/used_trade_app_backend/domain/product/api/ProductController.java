package com.example.used_trade_app_backend.domain.product.api;

import com.example.used_trade_app_backend.db.entity.ProductInfoEntity;
import com.example.used_trade_app_backend.domain.product.dto.ProductEnrollDto;
import com.example.used_trade_app_backend.domain.product.request.ProductEnrollRequest;
import com.example.used_trade_app_backend.domain.product.response.ProductEnrollResponse;
import com.example.used_trade_app_backend.domain.product.service.ProductService;
import com.example.used_trade_app_backend.exception.ErrorCode;
import com.example.used_trade_app_backend.exception.ProductException;
import com.example.used_trade_app_backend.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/enroll")
    public ResponseEntity<ProductEnrollResponse> enrollProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                               @RequestBody ProductEnrollRequest productEnrollRequest) {
        try {
            // 상품 등록 처리
            String token = authorizationHeader.replace("Bearer ", "");
            String userId = JwtUtil.extractUserId(token);
            ProductEnrollDto productEnrollDto = productEnrollRequest.toDto(userId);
            ProductInfoEntity createdProduct = productService.createProduct(productEnrollDto);

            // 성공적인 상품 등록 응답 생성
            ProductEnrollResponse response = new ProductEnrollResponse(createdProduct.getTitle(), ErrorCode.PRODUCT_ENROLL_SUCCESS);

            // 성공 응답 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ProductException ex) {
            // 상품 등록 중 오류 발생 시 오류 응답 반환
            ProductEnrollResponse response = new ProductEnrollResponse(ex.getErrorCode());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
