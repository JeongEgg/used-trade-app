package com.example.usedtradeapp.domain.oauth.api;

import com.example.usedtradeapp.domain.oauth.response.UserRegisterResponse;
import com.example.usedtradeapp.domain.oauth.request.UserInfoRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {

    @POST("/api/social-login")
    Call<UserRegisterResponse> sendUserInfo(@Body UserInfoRequest userInfoRequest);
}
