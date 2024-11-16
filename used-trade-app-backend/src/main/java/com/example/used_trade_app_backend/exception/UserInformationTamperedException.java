package com.example.used_trade_app_backend.exception;

public class UserInformationTamperedException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserInformationTamperedException(ErrorCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
