package com.example.localmarket;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.Order;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase de prueba para AuthService que realiza pruebas de integración con el servidor.
 */
public class AuthServiceOrderTest {

    /**
     * Prueba de integración que verifica si se reciben correctamente los datos del perfil de usuario desde el servidor.
     *
     *
     */
    @Test
    public void testGetUserProfile_IntegrationWithServer() {

        // Credenciales de usuario de prueba
        String username = "Bart123";
        String password = "A123456-";

        // Arrange
        AuthService authService = new AuthService();

        // Configurar el callback para capturar el resultado
        AuthServiceUnitTest.TestProfileCallback callback = new AuthServiceUnitTest.TestProfileCallback() {
            @Override
            public void onSuccess(User user) {
                // La obtención del perfil de usuario fue exitosa
                super.onSuccess(user);
                assertTrue(true);
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error si la prueba falla
                fail("Error: " + t.getMessage());
            }
        };

        // Act: Iniciar sesión para obtener el token
        System.out.println("Iniciando prueba de login...");
        authService.loginUser(username, password, new AuthServiceUnitTest.TestAuthCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                String token = response.getToken();
                System.out.println("Login exitoso. Token recibido: " + token);

                // Datos para obtener el perfil de usuario
                int userId = 13; // ID del usuario

                // Act: Realizar la solicitud de obtención del perfil de usuario
                System.out.println("Iniciando prueba de obtención del perfil de usuario...");
                authService.getUserProfile(userId, callback);
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error si la prueba de inicio de sesión falla
                fail("Error en el login: " + t.getMessage());
            }
        });

        // Esperar unos segundos para dar tiempo a la llamada asíncrona de completarse
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(callback.isSuccessful());
    }

    /**
     * Prueba de integración que verifica si se envía correctamente un pedido al servidor.
     */
    @Test
    public void testSendOrderToServer_IntegrationWithServer() {
        // Credenciales de usuario de prueba
        String username = "Bart123";
        String password = "A123456-";

        // Arrange: Configurar el pedido y el servicio de autenticación
        Order order = new Order();
        order.setIdCliente(17); // ID del cliente
        order.setIdAgricultor(5); // ID del agricultor
        List<Map<String, Integer>> productList = new ArrayList<>();
        Map<String, Integer> product1 = new HashMap<>();
        product1.put("itemId", 3); // ID del producto 1
        product1.put("cantidad", 2); // Cantidad del producto 1
        Map<String, Integer> product2 = new HashMap<>();
        product2.put("itemId", 4); // ID del producto 2
        product2.put("cantidad", 3); // Cantidad del producto 2
        productList.add(product1);
        productList.add(product2);
        order.setPedido(productList);
        order.setEstado("creado");
        order.setId(null);

        AuthService authService = new AuthService(); // Suponiendo que tienes una instancia adecuada de AuthService

        // Configurar el callback para capturar el resultado
        AuthServiceUnitTest.TestAuthCallback<Void> callback = new AuthServiceUnitTest.TestAuthCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                // El envío del pedido fue exitoso
                super.onSuccess(data);
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

                // Act: Realizar la solicitud de envío del pedido al servidor
                System.out.println("Iniciando prueba de envío de pedido al servidor...");
                authService.sendOrder(order,token, new AuthServiceUnitTest.TestAuthCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        // El envío del pedido fue exitoso
                        System.out.println("Envío de pedido exitoso.");
                        callback.onSuccess(data);
                    }

                    @Override
                    public void onError(Throwable t) {
                        // Manejar el error si el envío del pedido falla
                        System.out.println("Error al enviar el pedido: " + t.getMessage());
                        callback.onError(t);
                    }
                });
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
