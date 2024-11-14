package com.example.used_trade_app_backend.exception;

import com.example.used_trade_app_backend.signup.response.SignUpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SignUpControllerAdvice {
    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<SignUpResponse> handleSignUpException(SignUpException exception){
        SignUpResponse response = new SignUpResponse(exception.getErrorCode().code,exception.getErrorCode().message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
