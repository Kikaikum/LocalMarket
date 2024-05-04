package com.example.localmarket.network.api;

import com.example.localmarket.model.Order;
import com.example.localmarket.model.Product;
import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.ProductRequest;
import com.example.localmarket.model.ProductResponse;
import com.example.localmarket.model.SignUpRequest;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.model.UpdateEmailRequest;
import com.example.localmarket.model.UpdateNameRequest;
import com.example.localmarket.model.UpdatePasswordRequest;
import com.example.localmarket.model.UpdateSurnameRequest;
import com.example.localmarket.model.UpdateUsernameRequest;
import com.example.localmarket.model.User;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Oriol +Ainoha
 * Interfaz que define los métodos para interactuar con la API del servidor.
 * Define métodos para realizar operaciones como inicio de sesión, registro de usuario, obtener perfil de usuario, etc.
 * Las anotaciones como @GET, @POST, @PATCH, @DELETE se utilizan para especificar la acción HTTP correspondiente para cada método.
 * Cada método está asociado a una ruta de la API del servidor.
 * Las respuestas de los métodos son objetos Call que envuelven los datos devueltos por el servidor.
 */
public interface ApiService {

    /**
     * Método para iniciar sesión de usuario.
     *
     * @param loginRequest Objeto LoginRequest que contiene las credenciales de inicio de sesión.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Oriol Estero Sanchez
     */
    @POST("users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    /**
     * Método para crear un nuevo usuario.
     *
     * @param signUpRequest Objeto SignUpRequest que contiene los datos del usuario a registrar.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Oriol Estero Sanchez
     */
    @POST("users")
    Call<SignUpResponse> createUser(@Body SignUpRequest signUpRequest);

    /**
     * Método para obtener el perfil de un usuario por su ID.
     *
     * @param userId ID del usuario cuyo perfil se desea obtener.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Ainoha
     */
    @GET("users/{id}")
    Call<User> getUserProfile(@Path("id") int userId);

    /**
     * Método para obtener los datos de un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario del usuario cuyos datos se desean obtener.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Ainoha
     */
    @GET("users/username/{username}")
    Call<User> getUserData(@Path("username") String username);

    /**
     * Método para eliminar la cuenta de un usuario.
     *
     * @param userId ID del usuario cuya cuenta se desea eliminar.
     * @param token  Token de autorización del usuario.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author ainoha
     */
    @DELETE("users/{id}")
    Call<Void> deleteAccount(@Path("id") int userId, @Header("Authorization") String token);

    /**
     * Método para actualizar el nombre de usuario en la base de datos del servidor.
     *
     * @param userId                ID del usuario cuyo nombre se actualizará.
     * @param token                 Token de autorización del usuario.
     * @param updateUsernameRequest Objeto UpdateUsernameRequest que contiene el nuevo nombre de usuario.
     * @return Objeto Call que envuelve la respuesta del servidor.
     */
    @PATCH("users/{id}")
    Call<Void> updateUsername(@Path("id") int userId, @Header("Authorization") String token, @Body UpdateUsernameRequest updateUsernameRequest);

    /**
     * Método para actualizar el email del usuario en la base de datos del servidor.
     *
     * @param userId             ID del usuario cuyo email se actualizará.
     * @param token              Token de autorización del usuario.
     * @param updateEmailRequest Objeto UpdateEmailRequest que contiene el nuevo email del usuario.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Ainoha
     */
    @PATCH("users/{id}")
    Call<Void> updateEmail(@Path("id") int userId, @Header("Authorization") String token, @Body UpdateEmailRequest updateEmailRequest);

    /**
     * Método para actualizar el nombre del usuario en la base de datos del servidor.
     *
     * @param userId            ID del usuario cuyo nombre se actualizará.
     * @param token             Token de autorización del usuario.
     * @param updateNameRequest Objeto UpdateNameRequest que contiene el nuevo nombre del usuario.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Ainoha
     */
    @PATCH("users/{id}")
    Call<Void> updateName(@Path("id") int userId, @Header("Authorization") String token, @Body UpdateNameRequest updateNameRequest);

    /**
     * Método para actualizar los apellidos del usuario en la base de datos del servidor.
     *
     * @param userId               ID del usuario cuyos apellidos se actualizarán.
     * @param token                Token de autorización del usuario.
     * @param updateSurnameRequest Objeto UpdateSurnameRequest que contiene los nuevos apellidos del usuario.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Ainoha
     */
    @PATCH("users/{id}")
    Call<Void> updateSurname(@Path("id") int userId, @Header("Authorization") String token, @Body UpdateSurnameRequest updateSurnameRequest);

    /**
     * Método para actualizar la contraseña del usuario en la base de datos del servidor.
     *
     * @param userId                ID del usuario cuya contraseña se actualizará.
     * @param token                 Token de autorización del usuario.
     * @param updatePasswordRequest Objeto UpdatePasswordRequest que contiene la nueva contraseña del usuario.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Ainoha
     */
    @PATCH("users/{id}")
    Call<Void> updatePassword(@Path("id") int userId, @Header("Authorization") String token, @Body UpdatePasswordRequest updatePasswordRequest);


    /**
     * Método para obtener un producto por su ID.
     *
     * @param productId ID del producto que se desea obtener.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Oriol Estero Sanchez
     *
     */
    // @GET("productos/{id}")
    //Call<Product> getProductById(@Path("id") int productId);

    /**
     * Método para agregar un nuevo producto a la base de datos del servidor.
     *
     * @param product Objeto ProductRequest que contiene los detalles del nuevo producto.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Oriol Estero Sanchez
     */
    @POST("products")
    Call<ProductResponse> addProduct(@Header("id") int userId, @Header("Authorization") String token, @Body ProductRequest product);

    /**
     * Método para obtener todos los productos de la base de datos del servidor.
     *
     * @return Objeto Call que envuelve la respuesta del servidor.
     */
    @GET("products/agricultor/{id}")
    Call<List<Product>> getAllProducts(@Path("id") int userId, @Header("Authorization") String token);

    /**
     * Realiza una solicitud GET para obtener todos los productos del servidor.
     * @author Ainoha
     * @return Una llamada que devolverá una lista de productos cuando se realice.
     */
    @GET("products")
    Call<List<Product>> getAllProductsAvailable();

    /**
     * Método para actualizar un producto existente en la base de datos del servidor.
     *
     * @param productId      ID del producto que se actualizará.
     * @param token          Token de autorización del usuario.
     * @param updatedProduct Objeto Product que contiene los datos actualizados del producto.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Ainoha
     */
    @PATCH("products/{productId}")
    Call<Void> updateProduct(@Path("productId") int productId, @Header("Authorization") String token, @Body Product updatedProduct);

    /**
     * Método para eliminar un producto de la base de datos del servidor.
     *
     * @param productId ID del producto que se eliminará.
     * @param token     Token de autorización del usuario.
     * @return Objeto Call que envuelve la respuesta del servidor.
     * @author Ainoha
     */
    @DELETE("products/{productId}")
    Call<Void> deleteProduct(@Path("productId") int productId, @Header("Authorization") String token);

    @POST("orders")
    Call<Void> createOrder(@Header("idCliente") int idCliente, @Header("idAgricultor") int idAgricultor, @Body Order order);



    @GET("orders/agricultor/{id}")
    Call<List<Order>> getOrdersByAgricultor(@Path("id") int agricultorId);

    //@GET("location/")
    //Call<List<User>> getNearUsersFromMyLocation(@Body LatLng location);
    @GET("location/")
    Call<List<User>> getNearUsersFromMyLocation(@Query("latitude") double latitude, @Query("longitude") double longitude);
}