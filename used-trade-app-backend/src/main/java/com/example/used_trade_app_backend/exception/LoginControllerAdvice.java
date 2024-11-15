package com.example.used_trade_app_backend.exception;

import com.example.used_trade_app_backend.domain.login.response.UserLoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginControllerAdvice {
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<UserLoginResponse> handleLoginException(LoginException exception){
        UserLoginResponse response = new UserLoginResponse(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
