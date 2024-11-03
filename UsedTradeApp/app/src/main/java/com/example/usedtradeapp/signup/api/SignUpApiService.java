package com.example.usedtradeapp.signup.api;

import com.example.usedtradeapp.signup.request.SignUpRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpApiService {
    @POST("/sign-up") // 서버의 회원가입 엔드포인트
    Call<Void> signUp(@Body SignUpRequest signUpRequest);
}
