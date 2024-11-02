package com.example.usedtradeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.usedtradeapp.api.CRUDInterface;
import com.example.usedtradeapp.dto.MessageDto;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    MessageDto messageDto;
    CRUDInterface crudInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGet = findViewById(R.id.btn_get);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMessage();
            }
        });
    }

    private void getMessage() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<MessageDto> call = crudInterface.getMessage();
        call.enqueue(new Callback<MessageDto>() {
            @Override
            public void onResponse(Call<MessageDto> call, Response<MessageDto> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                messageDto = response.body();
                System.out.println(messageDto.toString());
            }

            @Override
            public void onFailure(Call<MessageDto> call, Throwable throwable) {

            }
        });
    }
}