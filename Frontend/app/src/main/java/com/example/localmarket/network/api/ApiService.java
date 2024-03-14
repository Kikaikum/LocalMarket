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
import retrofit2.http.PUT;

public interface ApiService {

    @POST("localmarket/v1/users")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("localmarket/v1/users/login")
    Call<SignUpResponse> createUser(@Body SignUpRequest signUpRequest);

    @GET("/user/profile")
    Call<User> getUserProfile();

    @DELETE("/user/delete")
    Call<Void> deleteAccount();

    @PUT("/update-username") // Ruta de la API para actualizar el nombre de usuario
    Call<Void> updateUsername(@Body String newUsername);
}
