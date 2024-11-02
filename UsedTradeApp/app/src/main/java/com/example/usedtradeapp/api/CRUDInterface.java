package com.example.usedtradeapp.api;

import com.example.usedtradeapp.dto.MessageDto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CRUDInterface {
    @GET("hello")
    Call<MessageDto> getMessage();
}
