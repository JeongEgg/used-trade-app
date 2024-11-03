package com.example.usedtradeapp.oauth.api;

import com.example.usedtradeapp.oauth.response.GoogleAccessTokenResponse;
import com.example.usedtradeapp.oauth.response.GoogleUserInfoResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GoogleApiService {
    @FormUrlEncoded
    @POST("token") // Google의 액세스 토큰 엔드포인트
    Call<GoogleAccessTokenResponse> getAccessToken(
            @Field("code") String code,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType
    );

    // 유저 정보를 요청하는 메소드
    @GET("https://www.googleapis.com/oauth2/v3/userinfo")
    Call<GoogleUserInfoResponse> getUserInfo(@Header("Authorization") String authorization);
}
