package com.example.usedtradeapp.oauth.api;

import com.example.usedtradeapp.oauth.request.UserInfoRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {

    @POST("user/info") // 스프링 서버의 엔드포인트를 설정합니다.
    Call<Void> sendUserInfo(@Body UserInfoRequest userInfoRequest);
}
