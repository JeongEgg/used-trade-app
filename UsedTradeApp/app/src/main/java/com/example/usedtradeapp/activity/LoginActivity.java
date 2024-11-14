package com.example.usedtradeapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.usedtradeapp.MainActivity;
import com.example.usedtradeapp.R;
import com.example.usedtradeapp.login.api.LoginService;
import com.example.usedtradeapp.oauth.redirect.GoogleAuthClient;
import com.example.usedtradeapp.oauth.api.GoogleApiService;
import com.example.usedtradeapp.oauth.response.GoogleAccessTokenResponse;
import com.example.usedtradeapp.oauth.response.GoogleUserInfoResponse;
import com.example.usedtradeapp.oauth.retrofit.GoogleRetrofitClient;
import com.example.usedtradeapp.oauth.api.UserService;
import com.example.usedtradeapp.oauth.utils.PropertiesUtil;
import com.google.gson.Gson;

import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences 이름
    private static final String JWT_TOKEN_KEY = "jwtToken"; // JWT 토큰 저장 키

    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String REDIRECT_URI;

    private EditText etEmail;
    private EditText etPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        if (checkJwtToken()) {
            finish();
            return;
        }

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        // GoogleAuthClient 인스턴스 생성
        GoogleAuthClient googleAuthClient = new GoogleAuthClient(this);

        // 로그인 버튼 클릭 시 OAuth 인증 페이지로 이동
        Properties properties = PropertiesUtil.loadProperties(this);
        CLIENT_ID = properties.getProperty("CLIENT_ID");
        REDIRECT_URI = properties.getProperty("REDIRECT_URI");
        CLIENT_SECRET = properties.getProperty("CLIENT_SECRET");
        findViewById(R.id.sign_in_button).setOnClickListener(view -> {
            googleAuthClient.authenticate(CLIENT_ID, REDIRECT_URI);
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 로그인 요청 서버로 전송
                LoginService.sendLoginRequest(getApplicationContext(), email, password);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 토큰이 저장되어 있는지 확인
                        if (isJwtTokenStored(getApplicationContext())) {
                            Log.d("LoginActivity", "토큰이 저장되었습니다.");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 새로운 작업으로 시작하고 모든 이전 Activity 종료
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d("LoginActivity", "토큰이 저장되지 않았습니다.");
                        }
                    }
                }, 1000);
            }
        });

        // 회원가입 버튼 클릭 시 회원가입 화면으로 이동
        findViewById(R.id.btn_sign_up_page).setOnClickListener(view -> {
            Intent signUpIntent = new Intent(this, SignUpActivity.class);
            startActivity(signUpIntent);
        });

        // 앱이 리디렉션 URI로 실행되었는지 확인하고, 인가 코드 추출
        handleIntent(getIntent());
    }

    private static boolean isJwtTokenStored(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.contains(JWT_TOKEN_KEY); // JWT 토큰이 존재하는지 체크
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
                    UserService.sendUserInfoToServer(getApplicationContext(), response.body());
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

    private boolean checkJwtToken() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.contains(JWT_TOKEN_KEY);
    }

    public static void saveJwtToken(Context context, String jwtToken) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(JWT_TOKEN_KEY, jwtToken);
        editor.apply();
    }
}