package com.example.usedtradeapp.domain.profile.api;


import com.example.usedtradeapp.domain.profile.response.ProfileFragmentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProfileApiService {
    @GET("/api/profile/fragment")  // 서버의 로그인 엔드포인트
    Call<ProfileFragmentResponse> getNickname(@Header("Authorization") String token);
}