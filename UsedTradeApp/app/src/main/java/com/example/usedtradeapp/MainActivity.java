package com.example.usedtradeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.usedtradeapp.activity.LoginActivity;
import com.example.usedtradeapp.activity.RegisterActivity;
import com.example.usedtradeapp.databinding.ActivityMainBinding;
import com.example.usedtradeapp.domain.oauth.utils.PropertiesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.Nullable;

import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences 이름
    private static final String USER_INFO_KEY = "userInfo"; // 저장할 유저 정보 키
    private static final String JWT_TOKEN_KEY = "jwtToken";

    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String REDIRECT_URI;

    private ActivityMainBinding binding;

    private static final int REGISTER_REQUEST_CODE = 100;
    private int previousSelectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // JWT 토큰 확인
        checkJwtToken();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_chat, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Bottom Navigation에서 등록 버튼을 클릭한 경우에는 등록 페이지 액티비티를 실행
        navView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_register) {
                previousSelectedItemId = navView.getSelectedItemId();


                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, REGISTER_REQUEST_CODE);
                return true;
            } else {

                previousSelectedItemId = item.getItemId(); // 선택 항목 업데이트
                NavigationUI.onNavDestinationSelected(item, navController);
                return true;
            }
        });

        // properties 값 가져오기
        Properties properties = PropertiesUtil.loadProperties(this);
        CLIENT_ID = properties.getProperty("CLIENT_ID");
        REDIRECT_URI = properties.getProperty("REDIRECT_URI");
        CLIENT_SECRET = properties.getProperty("CLIENT_SECRET");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_REQUEST_CODE && resultCode == RESULT_OK) {
            // 이전 선택 항목으로 BottomNavigationView 업데이트
            binding.navView.setSelectedItemId(previousSelectedItemId);
        }
    }

    private void checkJwtToken() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String jwtToken = prefs.getString(JWT_TOKEN_KEY, null);

        if (jwtToken == null) {
            Log.d("MainActivity", "No JWT Token found, redirecting to LoginActivity.");
            // JWT 토큰이 없으면 로그인 화면으로 이동
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();  // 현재 Activity 종료
        } else {
            Log.d("MainActivity", "JWT Token found.");
            Log.d("MainActivity", "JWT Token : "+jwtToken);
        }
    }
}