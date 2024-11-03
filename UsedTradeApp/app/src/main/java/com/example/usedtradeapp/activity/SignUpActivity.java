package com.example.usedtradeapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.usedtradeapp.R;
import com.example.usedtradeapp.common.retrofit.RetrofitClient;
import com.example.usedtradeapp.signup.api.SignUpApiService;
import com.example.usedtradeapp.signup.request.SignUpRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        Button btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(v -> {
            String name = ((EditText) findViewById(R.id.et_name)).getText().toString().trim();
            String email = ((EditText) findViewById(R.id.et_email)).getText().toString().trim();
            String password = ((EditText) findViewById(R.id.et_password1)).getText().toString().trim();
            String confirmPassword = ((EditText) findViewById(R.id.et_password2)).getText().toString().trim();

            if (!password.isEmpty() && password.equals(confirmPassword)) {
                signUpUser(name, email, password);
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUpUser(String name, String email, String password) {
        SignUpApiService apiService = RetrofitClient.createAuthService().create(SignUpApiService.class);
        SignUpRequest signUpRequest = new SignUpRequest(name, email, password);

        Call<Void> call = apiService.signUp(signUpRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("SignUp", "User signed up successfully.");
                    Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                    // 성공적으로 회원가입 되었을 때의 처리
                    finish();
                } else {
                    Log.e("SignUp", "Sign-up failed: " + response.message());
                    Toast.makeText(getApplicationContext(), "회원가입 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("SignUp", "Sign-up error: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "회원가입 에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}