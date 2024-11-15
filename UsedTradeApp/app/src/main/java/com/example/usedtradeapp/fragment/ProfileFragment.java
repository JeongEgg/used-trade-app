package com.example.usedtradeapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usedtradeapp.R;
import com.example.usedtradeapp.activity.ProfileActivity;
import com.example.usedtradeapp.common.retrofit.RetrofitClient;
import com.example.usedtradeapp.databinding.FragmentProfileBinding;
import com.example.usedtradeapp.domain.profile.api.ProfileApiService;
import com.example.usedtradeapp.domain.profile.response.ProfileFragmentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private static final String PREFS_NAME = "UserPrefs"; // SharedPreferences 이름
    private static final String JWT_TOKEN_KEY = "jwtToken";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        String jwtToken = getJwtToken(requireContext());
        if (jwtToken != null) {
            Log.d("ProfileFragment", "JWT Token: " + jwtToken);
            sendJwtTokenToServer(jwtToken);
        } else {
            Log.d("ProfileFragment", "JWT Token not found");
        }

        rootView.findViewById(R.id.layout_profile_revise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
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
        Call<ProfileFragmentResponse> call = apiService.getNickname(authorizationHeader);
        call.enqueue(new Callback<ProfileFragmentResponse>() {
            @Override
            public void onResponse(Call<ProfileFragmentResponse> call, Response<ProfileFragmentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String nickname = response.body().getNickname();
                    Log.d("ProfileFragment", "닉네임 : " + nickname);
                } else {
                    Log.e("ProfileFragment", "닉네임 가져오기 실패 : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileFragmentResponse> call, Throwable t) {
                Log.e("ProfileFragment", "JWT 토큰 전송 실패 : " + t.getMessage());
            }
        });
    }
}