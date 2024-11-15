package com.example.used_trade_app_backend.exception;

import com.example.used_trade_app_backend.domain.profile.response.ProfileFragmentResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfileControllerAdvice {
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ProfileFragmentResponse> handleProfileNotFoundException(ProfileNotFoundException ex) {
        ProfileFragmentResponse response = new ProfileFragmentResponse(ex.getErrorCode().message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);  // 404 반환
    }
}
