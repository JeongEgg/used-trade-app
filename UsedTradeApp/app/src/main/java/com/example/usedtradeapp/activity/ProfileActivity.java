package com.example.usedtradeapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.usedtradeapp.R;
import com.example.usedtradeapp.common.retrofit.RetrofitClient;
import com.example.usedtradeapp.domain.profile.api.ProfileApiService;
import com.example.usedtradeapp.domain.profile.response.ProfileActivityResponse;
import com.example.usedtradeapp.domain.profile.response.ProfileFragmentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences 이름
    private static final String JWT_TOKEN_KEY = "jwtToken";

    private TextView tvEmail;
    private EditText etName;
    private EditText etNickname;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvEmail = findViewById(R.id.tv_email_info);
        etName = findViewById(R.id.et_name_info);
        etNickname = findViewById(R.id.et_nickname_info);

        String jwtToken = getJwtToken(getApplicationContext());
        if (jwtToken != null) {
            Log.d("ProfileFragment", "JWT Token: " + jwtToken);
            sendJwtTokenToServer(jwtToken);
        } else {
            Log.d("ProfileFragment", "JWT Token not found");
        }

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getJwtToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(JWT_TOKEN_KEY, null); // JWT 토큰이 없으면 null 반환
    }

    private void sendJwtTokenToServer(String jwtToken) {
        // Retrofit 인스턴스 생성
        Retrofit retrofit = RetrofitClient.createAuthService();
        ProfileApiService apiService = retrofit.create(ProfileApiService.class);

        // "Bearer " 접두사를 붙여 Authorization 헤더 값으로 설정
        String authorizationHeader = "Bearer " + jwtToken;

        // 서버로 요청 보내기
        Call<ProfileActivityResponse> call = apiService.getUserInfo(authorizationHeader);
        call.enqueue(new Callback<ProfileActivityResponse>() {
            @Override
            public void onResponse(Call<ProfileActivityResponse> call, Response<ProfileActivityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    String email = response.body().getEmail();
                    String username = response.body().getUsername();
                    String nickname = response.body().getNickname();
                    tvEmail.setText(email);
                    etName.setText(username);
                    etNickname.setText(nickname);
                } else {
                    Log.e("ProfileFragment", "유저정보 가져오기 실패 : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileActivityResponse> call, Throwable t) {
                Log.e("ProfileFragment", "JWT 토큰 전송 실패 : " + t.getMessage());
            }
        });
    }
}