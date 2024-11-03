package com.example.usedtradeapp.api;

import retrofit2.Call;
import retrofit2.http.Query;

public interface GoogleAuthService {

    Call<Void> requestAuthCode(
            @Query("client_id") String clientId,
            @Query("redirect_uri") String redirectUri,
            @Query("response_type") String responseType,
            @Query("scope") String scope
    );
}
