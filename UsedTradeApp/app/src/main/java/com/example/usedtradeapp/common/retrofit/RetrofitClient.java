package com.example.usedtradeapp.common.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // 스프링 서버로 api 요청 전송
//    private static final String BASE_URL = "http://192.168.35.129:8080/";
    private static final String BASE_URL = "http://10.0.2.2:8080/"; // 서버의 주소로 변경
    public static Retrofit createAuthService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
