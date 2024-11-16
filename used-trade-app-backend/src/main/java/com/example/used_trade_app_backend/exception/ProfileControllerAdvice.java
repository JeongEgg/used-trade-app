package com.example.used_trade_app_backend.exception;

import com.example.used_trade_app_backend.domain.profile.response.ProfileFragmentResponse;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfileControllerAdvice {

    @ExceptionHandler(UserInformationTamperedException.class)
    public ResponseEntity<ErrorResponse> handleUserInformationTamperedException(UserInformationTamperedException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(errorCode.code, errorCode.message));
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ProfileFragmentResponse> handleProfileNotFoundException(ProfileNotFoundException ex) {
        ProfileFragmentResponse response = new ProfileFragmentResponse(ex.getErrorCode().message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);  // 404 반환
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private final int code;
        private final String message;
    }
}
