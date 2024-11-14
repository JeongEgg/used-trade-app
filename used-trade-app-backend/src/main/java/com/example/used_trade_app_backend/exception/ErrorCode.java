package com.example.used_trade_app_backend.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public enum ErrorCode {
    DUPLICATED_USER_ID(1001,"이미 존재하는 아이디입니다."),
    INVALID_USER_INPUT(1002,"잘못된 사용자 입력입니다."),
    INVALID_PASSWORD(1003,"비밀번호는 8자리 이상이어야 하며, 대문자, 소문자, 숫자, 특수 문자가 모두 포함되어야 합니다."),
    INTERNAL_SERVER_ERROR(1004,"서버 오류기 발생했습니다.");

    public final int code;
    public final String message;
}
