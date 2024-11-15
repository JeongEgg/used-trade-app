package com.example.usedtradeapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.usedtradeapp.R;
import com.example.usedtradeapp.common.retrofit.RetrofitClient;
import com.example.usedtradeapp.domain.oauth.response.GoogleUserInfoResponse;
import com.example.usedtradeapp.domain.signup.api.SignUpApiService;
import com.example.usedtradeapp.domain.signup.request.SignUpRequest;
import com.example.usedtradeapp.domain.signup.response.SignUpResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences 이름
    private static final String USER_INFO_KEY = "userInfo"; // 저장할 유저 정보 키

    private TextView tvErrorEmail;
    private TextView tvErrorPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        tvErrorEmail = findViewById(R.id.tv_error_email);
        tvErrorPassword = findViewById(R.id.tv_error_password);

        Button btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(v -> {
            String name = ((EditText) findViewById(R.id.et_name)).getText().toString().trim();
            String email = ((EditText) findViewById(R.id.et_email)).getText().toString().trim();
            String password = ((EditText) findViewById(R.id.et_password1)).getText().toString().trim();
            String confirmPassword = ((EditText) findViewById(R.id.et_password2)).getText().toString().trim();

            tvErrorEmail.setText("");
            tvErrorPassword.setText("");

            if (!password.isEmpty() && password.equals(confirmPassword)) {
                signUpUser(name, email, password);
            } else {
                tvErrorPassword.setText("비밀번호가 일치하지 않습니다.");
            }
        });
    }

    private void signUpUser(String name, String email, String password) {
        SignUpApiService apiService = RetrofitClient.createAuthService().create(SignUpApiService.class);
        SignUpRequest signUpRequest = new SignUpRequest(name, email, password);

        Call<SignUpResponse> call = apiService.signUp(signUpRequest);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    SignUpResponse responseBody = response.body();
                    Log.d("SignUpActivity", "응답 : " + responseBody.getMessage());
                    Toast.makeText(getApplicationContext(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String errorMessage = "";
                    int errorCode = 0;

                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorResponse);
                            errorMessage = jsonObject.getString("message");
                            errorCode = jsonObject.getInt("errorCode");
                        } catch (IOException | JSONException e) {
                            errorMessage = "알 수 없는 오류가 발생했습니다.";
                        }
                    }

                    Log.e("SignUpActivity", "회원가입 실패: " + errorMessage);
                    displayErrorMessage(errorCode, errorMessage);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.e("SignUpActivity", "회원가입 에러 : " + t.getMessage());
                Toast.makeText(getApplicationContext(), "회원가입 에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayErrorMessage(int errorCode, String errorMessage) {
        switch (errorCode) {
            case 1001:
                tvErrorEmail.setText(errorMessage);
                break;
            case 1002:
                tvErrorPassword.setText(errorMessage);
            case 1003:
                tvErrorPassword.setText(errorMessage);
                break;
            default:
                Toast.makeText(getApplicationContext(), "회원가입 실패: " + errorMessage, Toast.LENGTH_SHORT).show();
                break;
        }
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