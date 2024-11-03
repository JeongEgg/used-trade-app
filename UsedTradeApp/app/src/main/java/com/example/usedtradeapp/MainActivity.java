package com.example.usedtradeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.usedtradeapp.oauth.api.GoogleApiService;
import com.example.usedtradeapp.oauth.GoogleAuthClient;
import com.example.usedtradeapp.oauth.response.GoogleAccessTokenResponse;
import com.example.usedtradeapp.oauth.response.GoogleUserInfoResponse;
import com.example.usedtradeapp.oauth.retrofit.GoogleRetrofitClient;
import com.example.usedtradeapp.oauth.utils.PropertiesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences 이름
    private static final String USER_INFO_KEY = "userInfo"; // 저장할 유저 정보 키

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
                // userInfo 사용
            } catch (JsonSyntaxException e) {
                Log.e("UserInfo", "JSON Syntax Error: " + e.getMessage());
            } catch (Exception e) {
                Log.e("UserInfo", "Error: " + e.getMessage());
            }
        } else {
            Log.d("UserInfo", "No user info found");
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