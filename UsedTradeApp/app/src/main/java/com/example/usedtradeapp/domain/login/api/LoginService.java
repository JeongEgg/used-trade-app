package com.example.usedtradeapp.domain.login.api;

import android.content.Context;
import android.util.Log;

import com.example.usedtradeapp.activity.LoginActivity;
import com.example.usedtradeapp.common.retrofit.RetrofitClient;
import com.example.usedtradeapp.domain.login.request.LoginRequest;
import com.example.usedtradeapp.domain.login.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginService {
    private static final String TAG = "LoginService";

    private static final String PREFS_NAME = "UserPrefs";
    private static final String JWT_TOKEN_KEY = "jwtToken";

    // 로그인 요청을 서버로 보내고 JWT 토큰을 포함한 응답을 받는 메서드
    public static void sendLoginRequest(Context context, String email, String password) {
        LoginApiService loginApiService = RetrofitClient.createAuthService().create(LoginApiService.class);
        LoginRequest loginRequest = new LoginRequest(email, password);

        // 서버 응답을 LoginResponse로 받기
        Call<LoginResponse> call = loginApiService.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버 응답 처리
                    LoginResponse loginResponse = response.body();

                    // 서버 응답으로 받은 errorCode, message, token 출력
                    Log.d(TAG, "로그인 응답 성공");
                    Log.d(TAG, "ErrorCode: " + loginResponse.getErrorCode());
                    Log.d(TAG, "Message: " + loginResponse.getMessage());
                    Log.d(TAG, "Token: " + loginResponse.getToken());

                    // 성공적인 로그인인 경우, token만 저장
                    if (loginResponse.getErrorCode() == 1) {
                        LoginActivity.saveJwtToken(context.getApplicationContext(), loginResponse.getToken());
                    } else {
                        // 로그인 실패 시 메세지 출력
                        Log.e(TAG, "로그인 실패: " + loginResponse.getMessage());
                    }

                } else {
                    Log.e(TAG, "로그인 실패: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "로그인 에러: " + t.getMessage());
            }
        });
    }


}