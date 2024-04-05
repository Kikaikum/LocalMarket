package com.example.localmarket.network.service;

import com.example.localmarket.model.Product;
import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.ProductRequest;
import com.example.localmarket.model.ProductResponse;
import com.example.localmarket.model.SessionManager;
import com.example.localmarket.model.SignUpRequest;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.model.UpdateEmailRequest;
import com.example.localmarket.model.UpdateNameRequest;
import com.example.localmarket.model.UpdatePasswordRequest;
import com.example.localmarket.model.UpdateSurnameRequest;
import com.example.localmarket.model.UpdateUsernameRequest;
import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;
import com.example.localmarket.utils.TokenManager;

import java.util.List;

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
    private static final String BASE_URL = "https://kikaikum.ddns.net:3000/localmarket/v1/";
    private TokenManager tokenManager;

    public AuthService() {
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

    public void signUpUser(String email, String password, String username, String nombre, String apellidos, Boolean isVendor, final AuthCallback<SignUpResponse> callback) {
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

    public void getUserProfile(int id, AuthService.ProfileCallback callback) {
        apiService.getUserProfile(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        callback.onSuccess(user);
                    } else {
                        callback.onError(new Throwable("El usuario devuelto por el servidor es nulo"));
                    }
                } else {
                    callback.onError(new Throwable("Error en la respuesta del servidor: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void deleteAccount(int userId, String authToken, final AuthCallback<Void> callback) {
        // Construir el encabezado de autorización con el token como Bearer
        String authorizationHeader = "Bearer " + authToken;

        // Realizar la llamada a la API para eliminar la cuenta
        apiService.deleteAccount(userId, authorizationHeader).enqueue(new Callback<Void>() {
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



    public void updateUsername(int id, UpdateUsernameRequest updateUsernameRequest,String token, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar el nombre de usuario
        apiService.updateUsername(id, "Bearer " + token, updateUsernameRequest).enqueue(new Callback<Void>() {
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


    public void updateEmail(int id, UpdateEmailRequest updateEmailRequest, String token, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar el correo electrónico
        apiService.updateEmail(id, "Bearer " + token, updateEmailRequest).enqueue(new Callback<Void>() {
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

    public void updateName(int id, UpdateNameRequest updateNameRequest, String token,final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar el nombre
        apiService.updateName(id,"Bearer " + token, updateNameRequest).enqueue(new Callback<Void>() {
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

    public void updateSurname(int id, UpdateSurnameRequest editSurnameRequest,String token, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar el nombre
        apiService.updateSurname(id,"Bearer " + token, editSurnameRequest).enqueue(new Callback<Void>() {
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

    public void updatePassword(int id, UpdatePasswordRequest updatePasswordRequest,String token, final AuthCallback<Void> callback) {

        // Realizar la llamada a la API para actualizar password
        apiService.updatePassword(id, "Bearer " + token,updatePasswordRequest).enqueue(new Callback<Void>() {
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

    public void logoutUser(AuthCallback<Void> authCallback) {
        if (tokenManager != null) {
            tokenManager.clearToken();
        }
        authCallback.onSuccess(null);
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


    // Método para obtener los datos del usuario, incluido el password, desde el servidor
    public void getUserData(String username, final ProfileCallback callback) {
        // Realizar una solicitud GET al servidor para obtener los datos del usuario por su nombre de usuario
        Call<User> call = apiService.getUserData(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback con los datos del usuario
                    callback.onSuccess(response.body());
                } else {
                    // Si la respuesta no es exitosa, llamar al método onError del callback con el error
                    callback.onError(new Exception("Error al obtener los datos del usuario"));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback con el error
                callback.onError(t);
            }
        });
    }
    public void addProduct(ProductRequest product, final AuthCallback<ProductResponse> callback) {
        Call<ProductResponse> call = apiService.addProduct(product);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    // Si la solicitud fue exitosa, invoca el callback con la respuesta
                    callback.onSuccess(response.body());
                } else {
                    // Si la respuesta no fue exitosa, maneja el error
                    callback.onError(new Exception("Error al añadir producto: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Maneja fallos totales de la solicitud (por ejemplo, no hay conexión a internet)
                callback.onError(t);
            }
        });
    }
    public void getAllProducts(final AuthCallback<List<Product>> callback) {
        Call<List<Product>> call = apiService.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    // La solicitud fue exitosa. Usa callback para enviar la lista de productos a la UI
                    callback.onSuccess(response.body());
                } else {
                    // Maneja el caso donde la respuesta no es exitosa
                    callback.onError(new Exception("Error al obtener productos: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Maneja completamente el fallo de la solicitud (por ejemplo, problema de red)
                callback.onError(t);
            }
        });
    }
    public void updateProduct(int productId, Product updatedProduct, String token, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar el producto
        apiService.updateProduct(productId, "Bearer " + token, updatedProduct).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback
                    callback.onSuccess(null);
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al actualizar el producto"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }
    public void deleteProduct(int productId, String token, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para eliminar el producto
        apiService.deleteProduct(productId, "Bearer " + token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback
                    callback.onSuccess(null);
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al eliminar el producto"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }



    // Método para obtener una instancia de ApiService
    public ApiService getApiService() {
        return apiService;
    }

    public User getUser() {
        return sessionManager.getUser(); // O cualquier otra forma de obtener el usuario de la sesión actual
    }
}
