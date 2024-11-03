package com.example.usedtradeapp.retrofit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GoogleAuthClient {
    private static final String BASE_URL = "https://accounts.google.com/";

    private final Context context;

    public GoogleAuthClient(Context context) {
        this.context = context;
    }

    public void authenticate(String clientId, String redirectUri) {
        String url = BASE_URL + "o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=email profile";

        // 브라우저를 열어 URL로 이동 (사용자가 Google 로그인 페이지로 이동하도록 유도)
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
