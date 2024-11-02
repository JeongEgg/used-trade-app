package com.example.used_trade_app_backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class HelloController {

    private static Logger logger = LoggerFactory.getLogger(HelloController.class);
    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> getHello() {
        logger.info("getHello 요청됨");
        Map<String, String> response = new HashMap<>();
        response.put("message", "hello, world");
        return ResponseEntity.ok().body(response);
    }
}
