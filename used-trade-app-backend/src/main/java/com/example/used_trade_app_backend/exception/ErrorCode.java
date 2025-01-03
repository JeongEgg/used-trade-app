package com.example.used_trade_app_backend.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    SUCCESS(0, "회원가입이 성공적으로 완료되었습니다."),
    SUCCESS_LOGIN(1,"로그인 성공"),
    DUPLICATED_USER_ID(1001,"이미 존재하는 아이디입니다."),
    INVALID_PASSWORD_LENGTH(1002, "비밀번호는 8자리 이상이어야 합니다."),
    INVALID_PASSWORD_FORMAT(1003, "비밀번호는 대문자, 소문자, 숫자, 특수 문자가 모두 포함되어야 합니다."),
    USER_NOT_FOUND(1004, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(1005, "비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR(1006,"서버 오류기 발생했습니다."),

    INVALID_TOKEN(1007, "유효하지 않은 토큰입니다."),
    ACCESS_DENIED(1008, "접근이 거부되었습니다."),
    USER_INFORMATION_TAMPERED(1009, "사용자 정보가 위조되었습니다."),

    PRODUCT_ENROLL_SUCCESS(2000, "상품이 성공적으로 등록되었습니다."),
    DUPLICATE_PRODUCT_TITLE(2001, "상품 제목이 이미 존재합니다."),
    CATEGORY_NOT_FOUND(2002, "선택한 카테고리가 존재하지 않습니다.");

    public final int code;
    public final String message;
}
