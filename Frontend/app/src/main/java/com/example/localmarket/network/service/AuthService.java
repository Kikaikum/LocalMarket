package com.example.localmarket.network.service;

import android.util.Log;

import com.example.localmarket.context.ContextProvider;
import com.example.localmarket.model.Order;
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
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que gestiona la autenticación y las operaciones relacionadas con el usuario en la aplicación.
 * @author Oriol +Ainoha
 */

public class AuthService {

    private ApiService apiService;
    private static AuthService instance;
    private ProductRequest capturedProductRequest;
    private ContextProvider contextProvider=null;

    private SessionManager sessionManager;

    // URL base de tu API
    private static final String BASE_URL = "https://kikaikum.ddns.net:3000/localmarket/v1/";
    private TokenManager tokenManager;
    private User currentUser;

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
    public AuthService(ApiService apiService, ContextProvider contextProvider) {
        this.apiService = apiService;
        this.contextProvider = contextProvider;
    }

    /**
     * Método para iniciar sesión de un usuario en el sistema.
     *
     * @param userName Nombre de usuario del usuario.
     * @param password Contraseña del usuario.
     * @param callback Callback para manejar la respuesta del inicio de sesión.
     * @author Oriol Estero Sanchez
     */
    public void loginUser(String userName, String password, final AuthCallback<LoginResponse> callback) {
        LoginRequest loginRequest = new LoginRequest(userName, password);

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

    /**
     * Método para registrar un nuevo usuario en el sistema.
     *
     * @param email     Correo electrónico del usuario.
     * @param password  Contraseña del usuario.
     * @param username  Nombre de usuario del usuario.
     * @param nombre    Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @param isVendor  Indica si el usuario es un vendedor o no.
     * @param latitude   Indica la latitud del usuario en el mapa.
     * @param longitude Indica la longitud del usuario en el mapa.
     * @param callback  Callback para manejar la respuesta del registro.
     *
     * @author Oriol Estero Sanchez
     */
    public void signUpUser(String email, String password, String username, String nombre, String apellidos, Boolean isVendor, double latitude, double longitude, final AuthCallback<SignUpResponse> callback) {
        SignUpRequest signUpRequest = new SignUpRequest(email, password, username, nombre, apellidos, isVendor,latitude,longitude);
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
    /**
     * Obtiene el perfil del usuario desde el servidor utilizando su ID.
     * @param id ID del usuario cuyo perfil se desea obtener.
     * @param callback Callback que maneja las respuestas de la operación.
     * @author Ainoha
     */
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

    /**
     * Elimina la cuenta del usuario.
     * @param userId ID del usuario cuya cuenta se va a eliminar.
     * @param authToken Token de autenticación del usuario.
     * @param callback Callback que maneja las respuestas de la operación.
     * @author Ainoha
     */
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

    /**
     * Actualiza el nombre de usuario del usuario.
     * @param id ID del usuario cuyo nombre de usuario se va a actualizar.
     * @param updateUsernameRequest Objeto UpdateUsernameRequest que contiene el nuevo nombre de usuario.
     * @param token Token de autenticación del usuario.
     * @param callback Callback que maneja las respuestas de la operación.
     * @author Ainoha
     */
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

    /**
     * Actualiza el correo electrónico del usuario.
     * @param id ID del usuario cuyo correo electrónico se va a actualizar.
     * @param updateEmailRequest Objeto UpdateEmailRequest que contiene el nuevo correo electrónico.
     * @param token Token de autenticación del usuario.
     * @param callback Callback que maneja las respuestas de la operación.
     * @author Ainoha
     */
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

    /**
     * Actualiza el nombre del usuario.
     * @param id ID del usuario cuyo nombre se va a actualizar.
     * @param updateNameRequest Objeto UpdateNameRequest que contiene el nuevo nombre.
     * @param token Token de autenticación del usuario.
     * @param callback Callback que maneja las respuestas de la operación.
     * @author Ainoha
     */
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

    /**
     * Actualiza los apellidos del usuario.
     * @param id ID del usuario cuyos apellidos se van a actualizar.
     * @param editSurnameRequest Objeto UpdateSurnameRequest que contiene los nuevos apellidos.
     * @param token Token de autenticación del usuario.
     * @param callback Callback que maneja las respuestas de la operación.
     * @author Ainoha
     */
    public void updateSurname(int id, UpdateSurnameRequest editSurnameRequest,String token, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar los apellidos
        apiService.updateSurname(id,"Bearer " + token, editSurnameRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback
                    callback.onSuccess(null);
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al actualizar los apellidos"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }

    /**
     * Actualiza la contraseña del usuario.
     * @param id ID del usuario cuya contraseña se va a actualizar.
     * @param updatePasswordRequest Objeto UpdatePasswordRequest que contiene la nueva contraseña.
     * @param token Token de autenticación del usuario.
     * @param callback Callback que maneja las respuestas de la operación.
     * @author Ainoha
     */
    public void updatePassword(int id, UpdatePasswordRequest updatePasswordRequest,String token, final AuthCallback<Void> callback) {
        // Realizar la llamada a la API para actualizar la contraseña
        apiService.updatePassword(id, "Bearer " + token,updatePasswordRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, llamar al método onSuccess del callback
                    callback.onSuccess(null);
                } else {
                    // Si hay un error en la respuesta, llamar al método onError del callback
                    callback.onError(new Exception("Error al actualizar la contraseña"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }


    /**
     * Método para cerrar la sesión del usuario actual.
     *
     * @param authCallback Callback para manejar el resultado del cierre de sesión.
     * @author Oriol Estero Sanchez
     */
    public void logoutUser(AuthCallback<Void> authCallback) {
        // Implementa la lógica para cerrar la sesión del usuario actual, por ejemplo, limpiar el token de autenticación
        if (tokenManager != null) {
            tokenManager.clearToken();
        }
        // Llama al método onSuccess del callback para indicar que el cierre de sesión fue exitoso
        authCallback.onSuccess(null);
    }

    /**
     * Método para establecer el usuario actual.
     *
     * @param currentUser El usuario actual.
     * @author Oriol Estero Sanchez
     */
    public void setCurrentUser(User currentUser) {
        // Establece el usuario actual
        this.currentUser = currentUser;
    }

    /**
     * Método para obtener el usuario actual.
     *
     * @return El usuario actual o null si no hay usuario actualmente logueado.
     * @author Oriol Estero Sanchez
     */
    public Object getCurrentUser() {
        // Devuelve el usuario actual
        return currentUser;
    }

    public ProductRequest getCapturedProductRequest() {
        return capturedProductRequest;
    }



    /**
     * Callback para manejar el resultado de la obtención del perfil de usuario.
     *
     * @author Oriol Estero Sanchez
     */
    public interface ProfileCallback {
        void onSuccess(User userProfile); // Método llamado cuando se obtiene el perfil de usuario con éxito
        void onError(Throwable t); // Método llamado cuando ocurre un error al obtener el perfil de usuario
    }

    /**
     * Callback para manejar el resultado de las operaciones de autenticación.
     *
     * @param <T> Tipo de objeto que se espera recibir como respuesta en caso de éxito.
     * @author Oriol Estero Sanchez
     */
    public interface AuthCallback<T> {
        void onSuccess(T response); // Método llamado cuando la operación de autenticación tiene éxito
        void onError(Throwable t); // Método llamado cuando ocurre un error durante la operación de autenticación
    }

    /**
     * Método para obtener una instancia única de AuthService.
     *
     * @return Una instancia de AuthService.
     * @author Oriol Estero Sanchez
     */
    public static AuthService getInstance() {
        // Implementa la lógica para obtener una instancia única de AuthService, asegurándote de que solo haya una instancia creada
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }


    /**
     * Obtiene los datos del usuario, incluyendo la contraseña, desde el servidor utilizando su nombre de usuario.
     * @param username Nombre de usuario del usuario del que se desean obtener los datos.
     * @param callback Callback que maneja las respuestas de la operación.
     */
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

    public void addProduct(int id, ProductRequest product, String token, final AuthCallback<ProductResponse> callback) {
        capturedProductRequest = product;
        Call<ProductResponse> call = apiService.addProduct(id, "Bearer " + token, product);
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

    public void getAllProducts(int userId, String token, final AuthCallback<List<Product>> callback) {
        Call<List<Product>> call = apiService.getAllProducts(userId, "Bearer " + token);
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
                // Maneja el caso donde la solicitud falla
                callback.onError(t);
            }
        });
    }//cambis

    /**
     * Método para obtener todos los productos disponibles desde el servidor.
     *
     * @param callback El callback que será llamado cuando se complete la solicitud.
     */
    public void getAllProductsAvailable(final AuthCallback<List<Product>> callback) {
        Call<List<Product>> call = apiService.getAllProductsAvailable();
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

            /**
             * Maneja errores de conexión o de respuesta.
             *
             * @param call La llamada que generó el error.
             * @param t El objeto Throwable que indica el error.
             */
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Maneja errores de conexión
                callback.onError(t);
            }
        });
    }

    /**
     * Actualiza un producto en el servidor utilizando su ID y los datos actualizados del producto.
     * @param productId ID del producto que se desea actualizar.
     * @param updatedProduct Datos actualizados del producto.
     * @param token Token de autenticación necesario para realizar la operación.
     * @param callback Callback que maneja las respuestas de la operación.
     * @author Ainoha
     */
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
                System.out.println("Error en la llamada al servidor al actualizar el producto: " + t.getMessage());
                // Si hay un fallo en la llamada, llamar al método onError del callback
                callback.onError(t);
            }
        });
    }



    /**
     * Elimina un producto del servidor utilizando su ID.
     * @param productId ID del producto que se desea eliminar.
     * @param token Token de autenticación necesario para realizar la operación.
     * @param callback Callback que maneja las respuestas de la operación.
     * @author Ainoha
     */
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

    /**
     * Método para enviar un pedido al servidor.
     *
     * @param order Objeto Order que contiene la información del pedido.
     * @param callback Callback para manejar la respuesta del envío del pedido.
     * @author Ainoha
     */
    public void sendOrder(Order order, final AuthCallback<Void> callback) {
        int idCliente = order.getIdCliente();
        int idAgricultor = order.getIdAgricultor();

        apiService.createOrder(idCliente, idAgricultor, order).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(new Exception("Error al enviar el pedido"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void getUsersInRange(double latitude, double longitude, AuthCallback<List<User>> authCallback) {
        LatLng location = new LatLng(latitude, longitude);
        apiService.getNearUsersFromMyLocation(latitude,longitude).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> usersInRange = response.body();
                    authCallback.onSuccess(usersInRange);
                } else {
                    authCallback.onError(new Exception("Error fetching users in range: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                authCallback.onError(t);
            }
        });
    }

    public void getAllOrdersByFarmer(int agricultorId, AuthCallback<List<Order>> authCallback) {
        apiService.getOrdersByAgricultor(agricultorId).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    authCallback.onSuccess(response.body());
                } else {
                    authCallback.onError(new Exception("Error fetching orders"));
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                authCallback.onError(new Exception("Error: " + t.getMessage()));
            }
        });
    }
    public void getProductById(int productId, AuthCallback<Product> authCallback) {
        apiService.getProductById(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    // Aquí se asume que la respuesta del cuerpo es un 'Product'
                    authCallback.onSuccess(response.body()); // Correctamente se pasa un Product a onSuccess
                } else {
                    // Crea una excepción que describe el problema basado en el cuerpo de error de la respuesta
                    authCallback.onError(new Exception("Failed to fetch product, status code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                // Propaga la excepción con un mensaje de error más detallado
                authCallback.onError(new Exception("Network error: " + t.getMessage()));
            }
        });
    }
    public void updateOrderStatus(int orderId,String token, Map<String, String> statusUpdate, AuthCallback<Void> authCallback) {
        apiService.updateOrder(orderId, "Bearer " + token, statusUpdate).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("API", "Estado del pedido actualizado correctamente.");
                    authCallback.onSuccess(null);  // No hay datos específicos que retornar
                } else {
                    Log.e("API", "Falló la actualización del estado, código de respuesta: " + response.code());
                    authCallback.onError(new Exception("Error al actualizar el pedido, código: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API", "Error al realizar la solicitud: " + t.getMessage());
                authCallback.onError(new Exception(t));
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
