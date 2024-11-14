package com.example.used_trade_app_backend.login.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "ebiomeiombaldfalsdmfklawmlbkamblkaembkavmdlkffsd"; // Use a more secure key in production
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds

    public static String generateTokenBySocialLogin(String socialId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("socialId", socialId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String generateTokenByLogin(String userId, String username){
        return Jwts.builder()
                .setSubject(username)
                .claim("userId",userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
