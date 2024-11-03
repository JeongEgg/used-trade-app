package com.example.usedtradeapp.retrofit;

import com.example.usedtradeapp.api.AuthService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/"; // 서버의 주소로 변경

    public static AuthService createAuthService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AuthService.class);
    }
}
