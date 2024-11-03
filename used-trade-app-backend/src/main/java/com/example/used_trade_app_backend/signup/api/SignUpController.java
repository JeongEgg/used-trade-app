package com.example.used_trade_app_backend.signup.api;

import com.example.used_trade_app_backend.signup.request.SignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        logger.info("Received SignUpRequest: name={}, email={}, password={}",
                signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());
        return ResponseEntity.ok().body("");
    }
}