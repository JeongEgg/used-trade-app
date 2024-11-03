package com.example.usedtradeapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.usedtradeapp.R;
import com.example.usedtradeapp.oauth.redirect.GoogleAuthClient;
import com.example.usedtradeapp.oauth.api.GoogleApiService;
import com.example.usedtradeapp.oauth.response.GoogleAccessTokenResponse;
import com.example.usedtradeapp.oauth.response.GoogleUserInfoResponse;
import com.example.usedtradeapp.oauth.retrofit.GoogleRetrofitClient;
import com.example.usedtradeapp.oauth.api.UserService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences 이름
    private static final String USER_INFO_KEY = "userInfo"; // 저장할 유저 정보 키

    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String REDIRECT_URI;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // GoogleAuthClient 인스턴스 생성
        GoogleAuthClient googleAuthClient = new GoogleAuthClient(this);

        // 로그인 버튼 클릭 시 OAuth 인증 페이지로 이동
        findViewById(R.id.sign_in_button).setOnClickListener(view -> {
            googleAuthClient.authenticate(CLIENT_ID, REDIRECT_URI);
        });

        // 회원가입 버튼 클릭 시 회원가입 화면으로 이동
        findViewById(R.id.btn_sign_up_page).setOnClickListener(view -> {
            Intent signUpIntent = new Intent(this, SignUpActivity.class);
            startActivity(signUpIntent);
        });

        // 앱이 리디렉션 URI로 실행되었는지 확인하고, 인가 코드 추출
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Uri uri = intent.getData();
        if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
            // URL을 로그로 출력
            Log.d("OAuthRedirect", "Redirected URL: " + uri.toString());
            // 인가 코드 추출
            String code = uri.getQueryParameter("code");
            if (code != null) {
                Log.d("OAuthRedirect", "Authorization Code: " + code);
                requestGoogleAccessToken(code);
            } else {
                Log.d("OAuthRedirect", "Authorization Code not found");
            }
        }
    }

    public void requestGoogleAccessToken(String code) {
        GoogleApiService apiService = GoogleRetrofitClient.getClient().create(GoogleApiService.class);
        Call<GoogleAccessTokenResponse> call = apiService.getAccessToken(
                code,
                CLIENT_ID,
                CLIENT_SECRET,
                REDIRECT_URI,
                "authorization_code"
        );

        call.enqueue(new Callback<GoogleAccessTokenResponse>() {
            @Override
            public void onResponse(Call<GoogleAccessTokenResponse> call, Response<GoogleAccessTokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String accessToken = response.body().getAccessToken();
                    Log.d("AccessToken", "Access Token: " + accessToken);
                    requestUserInfo(accessToken);
                } else {
                    Log.e("AccessToken", "Failed to get access token: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GoogleAccessTokenResponse> call, Throwable t) {
                Log.e("AccessToken", "Error occurred: " + t.getMessage());
            }
        });
    }

    private void requestUserInfo(String accessToken) {
        GoogleApiService apiService = GoogleRetrofitClient.getClient().create(GoogleApiService.class);
        Call<GoogleUserInfoResponse> call = apiService.getUserInfo("Bearer " + accessToken);

        call.enqueue(new Callback<GoogleUserInfoResponse>() {
            @Override
            public void onResponse(Call<GoogleUserInfoResponse> call, Response<GoogleUserInfoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("UserInfo", "User Info: " + response.body().toString());
                    saveUserInfo(response.body()); // 유저 정보를 저장
                    UserService.sendUserInfoToServer(response.body());
                } else {
                    Log.e("UserInfo", "Failed to get user info: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GoogleUserInfoResponse> call, Throwable t) {
                Log.e("UserInfo", "Error occurred: " + t.getMessage());
            }
        });
    }

    private void saveUserInfo(GoogleUserInfoResponse userInfo) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // 사용자 정보를 JSON으로 변환하여 저장 (예시로 toString() 사용, 실제로는 JSON 라이브러리 사용 권장)
        Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);
        editor.putString(USER_INFO_KEY, userInfoJson);
        editor.apply();
    }

}