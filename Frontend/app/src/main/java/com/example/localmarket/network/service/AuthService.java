package com.example.localmarket.network.service;

import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.SignUpRequest;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthService {

    private ApiService apiService;
    private String URL = "https://2b3175a6-57df-49f6-ab1a-e1601987dadf.mock.pstmn.io";
    private static AuthService instance;
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

    public void signUpUser(String email, String password, String userName, String name, String surname, Boolean isVendor, final AuthCallback<SignUpResponse> callback) {
        SignUpRequest signUpRequest = new SignUpRequest(email, password, userName, name, surname, isVendor);
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

    public void getUserProfile(final ProfileCallback callback) {
        apiService.getUserProfile().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al obtener el perfil del usuario"));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void deleteAccount(final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para eliminar la cuenta
        apiService.deleteAccount().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback
                    callback.onSuccess(null);
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al eliminar la cuenta"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }

    public interface ProfileCallback {
        void onSuccess(User userProfile);
        void onError(Throwable t);
    }




    public interface AuthCallback<T> {
        void onSuccess(T response);
        void onError(Throwable t);
    }

    // Método para obtener una instancia única de AuthService
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    // Método para obtener una instancia de ApiService
    public ApiService getApiService() {
        return apiService;
    }
}
