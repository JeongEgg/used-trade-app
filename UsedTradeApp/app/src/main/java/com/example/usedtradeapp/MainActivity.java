package com.example.usedtradeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.usedtradeapp.api.AuthService;
import com.example.usedtradeapp.retrofit.GoogleAuthClient;
import com.example.usedtradeapp.retrofit.RetrofitClient;
import com.example.usedtradeapp.utils.PropertiesUtil;

import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private String CLIENT_ID;
    private String REDIRECT_URI;

    private AuthService authService;
    private String authorization_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // properties 값 가져오기
        Properties properties = PropertiesUtil.loadProperties(this);
        CLIENT_ID = properties.getProperty("CLIENT_ID");
        REDIRECT_URI = properties.getProperty("REDIRECT_URI");

        // GoogleAuthClient 인스턴스 생성
        GoogleAuthClient googleAuthClient = new GoogleAuthClient(this);

        // 로그인 버튼 클릭 시 OAuth 인증 페이지로 이동
        findViewById(R.id.sign_in_button).setOnClickListener(view -> {
            googleAuthClient.authenticate(CLIENT_ID, REDIRECT_URI);
        });
        findViewById(R.id.send_code_button).setOnClickListener(view -> {
            sendCode();
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
            authorization_code = code;
            if (code != null) {
                Log.d("OAuthRedirect", "Authorization Code: " + code);
            } else {
                Log.d("OAuthRedirect", "Authorization Code not found");
            }
        }
    }

    private void sendCode() {
        authService = RetrofitClient.createAuthService();
        Call<Void> call = authService.sendAuthCode(authorization_code);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {

            }
        });
    }
}