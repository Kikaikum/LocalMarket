package com.example.localmarket.network.api;

import android.text.style.UpdateAppearance;

import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.SignUpRequest;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.model.UpdateEmailRequest;
import com.example.localmarket.model.UpdateNameRequest;
import com.example.localmarket.model.UpdatePasswordRequest;
import com.example.localmarket.model.UpdateSurnameRequest;
import com.example.localmarket.model.UpdateUsernameRequest;
import com.example.localmarket.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("users")
    Call<SignUpResponse> createUser(@Body SignUpRequest signUpRequest);

    @GET("users/{id}")
    Call<User> getUserProfile(@Path("id") int userId);

    // Método para obtener los datos del usuario por su nombre de usuario
    @GET("users/username/{username}")
    Call<User> getUserData(@Path("username") String username);

    @DELETE("/user/delete")
    Call<Void> deleteAccount();
    @PATCH("users/{id}") // Ruta de la API para actualizar el nombre de usuario
    Call<Void> updateUsername(@Path("id") int userId, @Body UpdateUsernameRequest updateUsernameRequest);
    @PATCH("users/{id}") //Ruta de la API para actualizar el email
    Call<Void> updateEmail(@Path("id") int userId, @Body UpdateEmailRequest updateEmailRequest);

    @PATCH("users/{id}") //Ruta de la API para actualizar el nombre
    Call<Void> updateName(@Path("id") int userId, @Body UpdateNameRequest updateNameRequest);

    @PATCH("users/{id}") //Ruta de la API para actualizar apellidos
    Call<Void> updateSurname(@Path("id") int userId, @Body UpdateSurnameRequest updateSurnameRequest);

    @PATCH("users/username/{username}")//Ruta de la API para actualizar contraseña
    Call<Void> updatePassword(@Path("username") String username, @Body UpdatePasswordRequest updatePasswordRequest);

    @GET("users/username/{username}/password")
    Call<Boolean> verifyPassword(@Query("password") String password);
}
