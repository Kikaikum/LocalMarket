package com.example.localmarket.network.api;

import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.SignUpRequest;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("/signup")
    Call<SignUpResponse> createUser(@Body SignUpRequest signUpRequest);

    @GET("/user/profile")
    Call<User> getUserProfile();

    @DELETE("/user/delete")
    Call<Void> deleteAccount();

}
