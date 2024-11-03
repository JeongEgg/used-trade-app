package com.example.usedtradeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.usedtradeapp.api.GoogleApiService;
import com.example.usedtradeapp.oauth.GoogleAuthClient;
import com.example.usedtradeapp.response.GoogleAccessTokenResponse;
import com.example.usedtradeapp.response.GoogleUserInfoResponse;
import com.example.usedtradeapp.retrofit.GoogleRetrofitClient;
import com.example.usedtradeapp.utils.PropertiesUtil;

import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String REDIRECT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // properties 값 가져오기
        Properties properties = PropertiesUtil.loadProperties(this);
        CLIENT_ID = properties.getProperty("CLIENT_ID");
        REDIRECT_URI = properties.getProperty("REDIRECT_URI");
        CLIENT_SECRET = properties.getProperty("CLIENT_SECRET");


        // GoogleAuthClient 인스턴스 생성
        GoogleAuthClient googleAuthClient = new GoogleAuthClient(this);
        // 로그인 버튼 클릭 시 OAuth 인증 페이지로 이동
        findViewById(R.id.sign_in_button).setOnClickListener(view -> {
            googleAuthClient.authenticate(CLIENT_ID, REDIRECT_URI);
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
            requestGoogleAccessToken(code);
            if (code != null) {
                Log.d("OAuthRedirect", "Authorization Code: " + code);
            } else {
                Log.d("OAuthRedirect", "Authorization Code not found");
            }
        }
    }

    public void requestGoogleAccessToken(String code) {
        GoogleApiService apiService = GoogleRetrofitClient.getClient().create(GoogleApiService.class);
        Call<GoogleAccessTokenResponse> call = apiService.getAccessToken(
                code,
                CLIENT_ID,         // GCP에서 생성한 클라이언트 ID
                CLIENT_SECRET,     // GCP에서 생성한 클라이언트 비밀
                REDIRECT_URI,      // 리디렉션 URI
                "authorization_code"      // grant_type
        );

        call.enqueue(new Callback<GoogleAccessTokenResponse>() {
            @Override
            public void onResponse(Call<GoogleAccessTokenResponse> call, Response<GoogleAccessTokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String accessToken = response.body().getAccessToken();
                    // 액세스 토큰을 로그로 출력
                    Log.d("AccessToken", "Access Token: " + accessToken);
                    // 액세스 토큰을 사용하여 API 요청 등을 수행
                    // 예: SharedPreferences에 저장하거나 API 호출에 사용
                    requestUserInfo(accessToken);
                } else {
                    // 오류 처리
                    Log.e("AccessToken", "Failed to get access token: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GoogleAccessTokenResponse> call, Throwable t) {
                // 요청 실패 처리
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
                    // 사용자 정보를 로그로 출력
                    Log.d("UserInfo", "User Info: " + response.body().toString());
                } else {
                    // 오류 처리
                    Log.e("UserInfo", "Failed to get user info: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GoogleUserInfoResponse> call, Throwable t) {
                // 요청 실패 처리
                Log.e("UserInfo", "Error occurred: " + t.getMessage());
            }
        });
    }
}