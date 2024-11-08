package com.example.usedtradeapp.oauth.api;

import com.example.usedtradeapp.oauth.request.UserInfoRequest;
import com.example.usedtradeapp.oauth.response.UserRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {

    @POST("/api/social-login")
    Call<UserRegisterResponse> sendUserInfo(@Body UserInfoRequest userInfoRequest);
}
