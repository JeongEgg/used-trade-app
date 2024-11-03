package com.example.used_trade_app_backend.oauth.exception;

public class LoginException extends BaseException {
    private final LoginExceptionType exceptionType;

    public LoginException(final LoginExceptionType exceptionType) {
        super(exceptionType.errorMessage());
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}