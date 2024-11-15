package com.example.usedtradeapp.domain.login.api;

import com.example.usedtradeapp.domain.login.request.LoginRequest;
import com.example.usedtradeapp.domain.login.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {

    @POST("/api/login")  // 서버의 로그인 엔드포인트
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
