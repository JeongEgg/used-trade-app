package com.example.usedtradeapp.login.api;

import com.example.usedtradeapp.login.request.LoginRequest;
import com.example.usedtradeapp.login.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {

    @POST("/api/login")  // 서버의 로그인 엔드포인트
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
