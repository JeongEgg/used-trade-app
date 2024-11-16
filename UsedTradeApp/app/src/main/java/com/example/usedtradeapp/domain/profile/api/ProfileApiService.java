package com.example.usedtradeapp.domain.profile.api;


import com.example.usedtradeapp.domain.profile.request.ProfileUpdateRequest;
import com.example.usedtradeapp.domain.profile.response.ProfileActivityResponse;
import com.example.usedtradeapp.domain.profile.response.ProfileFragmentResponse;
import com.example.usedtradeapp.domain.profile.response.ProfileUpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ProfileApiService {
    @GET("/api/profile/fragment")  // 서버의 로그인 엔드포인트
    Call<ProfileFragmentResponse> getNickname(@Header("Authorization") String token);

    @GET("/api/profile/activity")
    Call<ProfileActivityResponse>getUserInfo(@Header("Authorization") String token);

    @POST("/api/profile/activity")
    Call<ProfileUpdateResponse>updateUserInfo(@Header("Authorization") String token, @Body ProfileUpdateRequest profileUpdateRequest);
}
