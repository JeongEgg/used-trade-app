package com.example.usedtradeapp.signup.api;

import com.example.usedtradeapp.signup.request.SignUpRequest;
import com.example.usedtradeapp.signup.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpApiService {
    @POST("/api/sign-up") // 서버의 회원가입 엔드포인트
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);
}
