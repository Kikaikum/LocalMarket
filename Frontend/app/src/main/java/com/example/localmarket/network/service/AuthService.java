package com.example.localmarket.network.service;

import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.SignUpRequest;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.network.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthService {

    private ApiService apiService;
    private String URL = "https://2b3175a6-57df-49f6-ab1a-e1601987dadf.mock.pstmn.io";

    public AuthService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL) // URL base de tu API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void loginUser(String email, String password, final AuthCallback<LoginResponse> callback) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error en el login"));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void signUpUser(String email, String password, String name, final AuthCallback<SignUpResponse> callback) {
        SignUpRequest signUpRequest = new SignUpRequest(email, password, name);
        apiService.createUser(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error en el registro"));
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface AuthCallback<T> {
        void onSuccess(T response);
        void onError(Throwable t);
    }
}
