package com.example.usedtradeapp.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {

    @FormUrlEncoded
    @POST("/oauth2/code")
    Call<Void> sendAuthCode(@Field("code") String code);
}
