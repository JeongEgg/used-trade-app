package com.example.usedtradeapp.oauth.api;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.usedtradeapp.MainActivity;
import com.example.usedtradeapp.activity.LoginActivity;
import com.example.usedtradeapp.oauth.request.UserInfoRequest;
import com.example.usedtradeapp.oauth.response.GoogleUserInfoResponse;
import com.example.usedtradeapp.common.retrofit.RetrofitClient;
import com.example.usedtradeapp.oauth.response.UserRegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    private static final String TAG = "UserService";

    // 사용자 정보를 서버로 전송하고 JWT 토큰을 포함한 응답을 받는 메서드
    public static void sendUserInfoToServer(Context context, GoogleUserInfoResponse userInfo) {
        UserApiService userApiService = RetrofitClient.createAuthService().create(UserApiService.class);
        UserInfoRequest userInfoRequest = new UserInfoRequest(userInfo.getId(), userInfo.getName(), userInfo.getEmail());

        // 서버 응답을 UserRegisterResponse로 받기
        Call<UserRegisterResponse> call = userApiService.sendUserInfo(userInfoRequest);
        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 응답으로 받은 사용자 정보와 JWT 토큰
                    UserRegisterResponse userResponse = response.body();

                    Log.d(TAG, "User info sent to server successfully");
                    Log.d(TAG, "User ID: " + userResponse.getId());
                    Log.d(TAG, "Social ID: " + userResponse.getSocialId());
                    Log.d(TAG, "Username: " + userResponse.getUsername());
                    Log.d(TAG, "Nickname: " + userResponse.getNickname());
                    Log.d(TAG, "JWT Token: " + userResponse.getToken());

                    // JWT 토큰을 SharedPreferences에 저장하거나 다른 곳에서 사용할 수 있음
                    LoginActivity.saveJwtToken(context.getApplicationContext()
                            , userResponse.getToken());
                } else {
                    Log.e(TAG, "Failed to send user info: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                Log.e(TAG, "Error sending user info: " + t.getMessage());
            }
        });
    }
}
