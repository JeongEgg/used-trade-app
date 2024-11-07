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
import com.example.usedtradeapp.oauth.response.GoogleUserInfoResponse;
import com.example.usedtradeapp.oauth.api.UserService;
import com.example.usedtradeapp.oauth.utils.PropertiesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.jetbrains.annotations.Nullable;

import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences 이름
    private static final String USER_INFO_KEY = "userInfo"; // 저장할 유저 정보 키

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

        // 앱이 실행될 때 사용자 정보를 로드
        loadUserInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_REQUEST_CODE && resultCode == RESULT_OK) {
            // 이전 선택 항목으로 BottomNavigationView 업데이트
            binding.navView.setSelectedItemId(previousSelectedItemId);
        }
    }

    private void loadUserInfo() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userInfoJson = prefs.getString(USER_INFO_KEY, null);

        if (userInfoJson != null) {
            Log.d("UserInfo", "Loaded user info: " + userInfoJson);
            try {
                Gson gson = new Gson();
                GoogleUserInfoResponse userInfo = gson.fromJson(userInfoJson, GoogleUserInfoResponse.class);
                logUserInfo(userInfo);
                UserService.sendUserInfoToServer(userInfo);

                // userInfo 사용
            } catch (JsonSyntaxException e) {
                Log.e("UserInfo", "JSON Syntax Error: " + e.getMessage());
            } catch (Exception e) {
                Log.e("UserInfo", "Error: " + e.getMessage());
            }
        } else {
            Log.d("UserInfo", "No user info found");
            // 로그인 화면으로 이동
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void logUserInfo(GoogleUserInfoResponse userInfo) {
        // 사용자 정보를 한 줄씩 로그로 출력
        Log.d("UserInfo", "User ID: " + userInfo.getId());
        Log.d("UserInfo", "User Name: " + userInfo.getName());
        Log.d("UserInfo", "User Email: " + userInfo.getEmail());
        Log.d("UserInfo", "User Picture: " + userInfo.getPictureUrl());
    }
}