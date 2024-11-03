package com.example.usedtradeapp.oauth.api;

import android.util.Log;

import com.example.usedtradeapp.oauth.request.UserInfoRequest;
import com.example.usedtradeapp.oauth.response.GoogleUserInfoResponse;
import com.example.usedtradeapp.oauth.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    private static final String TAG = "UserService";

    // 사용자 정보를 서버로 전송하는 메서드
    public static void sendUserInfoToServer(GoogleUserInfoResponse userInfo) {
        UserApiService userApiService = RetrofitClient.createAuthService().create(UserApiService.class);
        UserInfoRequest userInfoRequest = new UserInfoRequest(userInfo.getId(), userInfo.getName(), userInfo.getEmail());

        Call<Void> call = userApiService.sendUserInfo(userInfoRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "User info sent to server successfully");
                } else {
                    Log.e(TAG, "Failed to send user info: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error sending user info: " + t.getMessage());
            }
        });
    }
}
