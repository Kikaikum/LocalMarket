package com.example.localmarket.network.service;

import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.SessionManager;
import com.example.localmarket.model.SignUpRequest;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class AuthService {

    private ApiService apiService;
    private static AuthService instance;

    private SessionManager sessionManager;

    // URL base de tu API
    private static final String BASE_URL = "https://kikaikum.ddns.net:3000/";

    private AuthService() {
        // Configuración del cliente HTTP con interceptor para logs
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        // Creación de instancia de Retrofit con la URL base y el cliente HTTP configurado
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        // Creación de la interfaz de servicio a partir de Retrofit
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

    public void signUpUser(String email, String password, String username, String nombre, String apellidos, String isVendor, final AuthCallback<SignUpResponse> callback) {
        SignUpRequest signUpRequest = new SignUpRequest(email, password, username, nombre, apellidos, isVendor);
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

    public void updateUsername(String newUsername, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar el nombre de usuario
        apiService.updateUsername(newUsername).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback
                    callback.onSuccess(null);
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al actualizar el nombre de usuario"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }

    public void updateEmail(String newEmail, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar el correo electrónico
        apiService.updateEmail(newEmail).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback
                    callback.onSuccess(null);
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al actualizar el correo electrónico"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }

    public void updateName(String newName, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar el nombre
        apiService.updateName(newName).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback
                    callback.onSuccess(null);
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al actualizar el nombre"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }

    public void updateSurname(String newSurname, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar el nombre
        apiService.updateSurname(newSurname).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback
                    callback.onSuccess(null);
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al actualizar el nombre"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }

    public void updatePassword(String actualPassword, String newPassword, final AuthCallback<Void> callback) {
        // Verificar la contraseña actual antes de actualizarla
        verifyPassword(actualPassword, new AuthCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean isCorrect) {
                if (isCorrect) {
                    // La contraseña actual es correcta, proceder a actualizarla
                    apiService.updatePassword(newPassword).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                callback.onSuccess(null);
                            } else {
                                callback.onError(new Exception("Error al actualizar la contraseña"));
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            callback.onError(t);
                        }
                    });
                } else {
                    // La contraseña actual es incorrecta, llamar al método onError del callback
                    callback.onError(new Exception("La contraseña actual es incorrecta"));
                }
            }

            @Override
            public void onError(Throwable t) {
                // Si hay un error al verificar la contraseña, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }


    public void verifyPassword(String password, final AuthCallback<Boolean> callback) {
        // Realizar la llamada a la API para verificar la contraseña
        apiService.verifyPassword(password).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback con el resultado de la verificación
                    callback.onSuccess(response.body());
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al verificar la contraseña"));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
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
