package com.example.usedtradeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.usedtradeapp.activity.LoginActivity;
import com.example.usedtradeapp.databinding.ActivityMainBinding;
import com.example.usedtradeapp.oauth.response.GoogleUserInfoResponse;
import com.example.usedtradeapp.oauth.api.UserService;
import com.example.usedtradeapp.oauth.utils.PropertiesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences 이름
    private static final String USER_INFO_KEY = "userInfo"; // 저장할 유저 정보 키

    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String REDIRECT_URI;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_register, R.id.navigation_chat, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // properties 값 가져오기
        Properties properties = PropertiesUtil.loadProperties(this);
        CLIENT_ID = properties.getProperty("CLIENT_ID");
        REDIRECT_URI = properties.getProperty("REDIRECT_URI");
        CLIENT_SECRET = properties.getProperty("CLIENT_SECRET");

        // 앱이 실행될 때 사용자 정보를 로드
        loadUserInfo();
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