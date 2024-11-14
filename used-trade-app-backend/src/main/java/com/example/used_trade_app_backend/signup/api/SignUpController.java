package com.example.used_trade_app_backend.signup.api;

import com.example.used_trade_app_backend.signup.request.SignUpRequest;
import com.example.used_trade_app_backend.signup.response.SignUpResponse;
import com.example.used_trade_app_backend.signup.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
public class SignUpController {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        logger.info("Received SignUpRequest: name={}, email={}, password={}",
                signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());

        String signupResponseMessage = userService.signup(signUpRequest);
        SignUpResponse signupResponse = new SignUpResponse(signupResponseMessage);
        return ResponseEntity.ok().body(signupResponse);
    }
}