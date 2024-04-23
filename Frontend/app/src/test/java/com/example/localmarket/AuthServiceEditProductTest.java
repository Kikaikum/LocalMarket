package com.example.localmarket;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.Product;
import com.example.localmarket.model.UpdateEmailRequest;
import com.example.localmarket.model.UpdateNameRequest;
import com.example.localmarket.model.UpdatePasswordRequest;
import com.example.localmarket.model.UpdateSurnameRequest;
import com.example.localmarket.model.UpdateUsernameRequest;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Clase de prueba para probar las peticiones al servidor de actualizar datos de producto
 * Esta clase de prueba verifica la correcta conexion con el servidor u actualizacion de datos y falla en caso de falta de conexion.
 * @author Ainoha
 */
public class AuthServiceEditProductTest {
    private String token;
    private AuthService authService;

    /**
     * Configura el entorno de prueba antes de cada método de prueba.
     */
    @Before
    public void setUp() {
        // Inicializar la instancia de AuthService
        authService = new AuthService();
    }





    /**
     * Prueba la funcionalidad actualizar producto
     */
        @Test
        public void testUpdateProduct_IntegrationWithServer() {
            // Credenciales de usuario de prueba
            String username = "Apu123";
            String password = "B123456-";



            // Arrange
            AuthService authService = new AuthService(); // Suponiendo que tienes una instancia adecuada de AuthService


            // Configurar el callback para capturar el resultado
            AuthServiceUnitTest.TestAuthCallback<Void> callback = new AuthServiceUnitTest.TestAuthCallback<Void>() {
                @Override
                public void onSuccess(Void response) {
                    // La actualización del producto fue exitosa
                    super.onSuccess(null);
                    assertTrue(true);
                }

                @Override
                public void onError(Throwable t) {
                    // Manejar el error si la prueba falla
                    fail("Error: " + t.getMessage());
                }
            };

            // Iniciar sesión para obtener el token
            AuthServiceUnitTest.TestAuthCallback<LoginResponse> loginCallback = new AuthServiceUnitTest.TestAuthCallback<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse response) {
                    String token = response.getToken();

                    // Llama al método onSuccess de la clase padre para capturar la respuesta
                    super.onSuccess(response);
                    System.out.println("Login exitoso. Token recibido: " + response.getToken());


                    // Datos de prueba del producto
                    String name = "Manzanas test2";
                    int categoriaId = 2131165304;
                    String description = "Descripción dtest2";
                    String unidadMedida = "peso";
                    double precio = 2.22;
                    double stock = 222;
                    int productId=49;

                    Product updatedProduct = new Product(name,categoriaId, precio, description, unidadMedida, stock);
                    // Act: Realizar la solicitud de actualización del producto
                    System.out.println("Iniciando prueba de actualización de producto..");
                    // Act: Realizar la solicitud de actualización del producto
                    AuthService.getInstance().updateProduct(productId, updatedProduct, token, callback);
                }

                @Override
                public void onError(Throwable t) {
                    // Manejar el error si la prueba de inicio de sesión falla
                    fail("Error en el login: " + t.getMessage());
                }
            };

            // Act: Iniciar sesión para obtener el token
            authService.loginUser(username, password, loginCallback);

            // Esperar unos segundos para dar tiempo a la llamada asíncrona de completarse
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertTrue(callback.isSuccessful());
        }
    }
