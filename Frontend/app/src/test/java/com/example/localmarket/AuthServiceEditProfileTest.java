package com.example.localmarket;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.UpdateEmailRequest;
import com.example.localmarket.model.UpdateNameRequest;
import com.example.localmarket.model.UpdatePasswordRequest;
import com.example.localmarket.model.UpdateSurnameRequest;
import com.example.localmarket.model.UpdateUsernameRequest;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * Clase de prueba para probar las peticiones al servidor de actualizar datos de usurio.
 * Esta clase de prueba verifica la correcta conexion con el servidor u actualizacion de datos y falla en caso de falta de conexion.
 * @author Ainoha
 */
public class AuthServiceEditProfileTest {
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
     * Prueba la funcionalidad de inicio de sesión de usuarioy permite realizar los siguientes tests pasando el token
     */
    @Test
    public void testLoginUser_Success() {

        // Arrange
        AuthService authService = new AuthService();
        String userName = "Apu123";
        String password ="B123456-";

        // Configuración del callback para capturar el resultado
        AuthServiceUnitTest.TestAuthCallback<LoginResponse> callback = new AuthServiceUnitTest.TestAuthCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                token = response.getToken();
                // Llama al método onSuccess de la clase padre para capturar la respuesta
                super.onSuccess(response);
                System.out.println("Login exitoso. Token recibido: " + response.getToken());
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error si la prueba falla
                fail("Error: " + t.getMessage());
            }
        };

        // Act
        System.out.println("Iniciando prueba de login...");
        authService.loginUser(userName, password, callback);

        // Espera unos segundos para dar tiempo a la llamada asíncrona de completarse
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert
        assertTrue(callback.isSuccessful());
        assertNotNull(callback.getResponse().getToken());
    }

    /**
     * Prueba la funcionalidad actualizar email.
     */
    @Test
    public void testUpdateEmail_IntegrationWithServer() {

        // Credenciales de usuario de prueba
        String username = "Apu123";
        String password = "B123456-";

        // Arrange
        AuthService authService = new AuthService();

        // Configurar el callback para capturar el resultado
        AuthServiceUnitTest.TestAuthCallback<Void> callback = new AuthServiceUnitTest.TestAuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // La actualización del correo electrónico fue exitosa
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
                token = response.getToken();
                // Llama al método onSuccess de la clase padre para capturar la respuesta
                super.onSuccess(response);
                System.out.println("Login exitoso. Token recibido: " + response.getToken());

                // Datos para la actualización del correo electrónico
                int userId = 13; // ID del usuario
                String newEmail = "newwwww_correo@example.com";
                UpdateEmailRequest updateEmailRequest = new UpdateEmailRequest(userId, newEmail, token);

                // Act: Realizar la solicitud de actualización del correo electrónico
                System.out.println("Iniciando prueba de actualización de correo electrónico...");
                authService.updateEmail(userId, updateEmailRequest, token, callback);
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error si la prueba de inicio de sesión falla
                fail("Error en el login: " + t.getMessage());
            }
        };

        // Act: Iniciar sesión para obtener el token
        System.out.println("Iniciando prueba de login...");
        authService.loginUser(username, password, loginCallback);

        // Esperar unos segundos para dar tiempo a la llamada asíncrona de completarse
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(callback.isSuccessful());
    }
    /**
     * Prueba la funcionalidad de actualizar nombre de usuario.
     */
    @Test
    public void testUpdateName_IntegrationWithServer() {

        // Credenciales de usuario de prueba
        String username = "Apu123";
        String password = "B123456-";

        // Arrange
        AuthService authService = new AuthService();

        // Configurar el callback para capturar el resultado
        AuthServiceUnitTest.TestAuthCallback<Void> callback = new AuthServiceUnitTest.TestAuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // La actualización del correo electrónico fue exitosa
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
                token = response.getToken();
                // Llama al método onSuccess de la clase padre para capturar la respuesta
                super.onSuccess(response);
                System.out.println("Login exitoso. Token recibido: " + response.getToken());

                // Datos para la actualización del correo electrónico
                int userId = 13; // ID del usuario
                String newName = "ApuuuuBadu";
                UpdateNameRequest updateNameRequest = new UpdateNameRequest(userId, newName, token);

                // Act: Realizar la solicitud de actualización del correo electrónico
                System.out.println("Iniciando prueba de actualización de correo electrónico...");
                authService.updateName(userId, updateNameRequest, token, callback);
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error si la prueba de inicio de sesión falla
                fail("Error en el login: " + t.getMessage());
            }
        };

        // Act: Iniciar sesión para obtener el token
        System.out.println("Iniciando prueba de login...");
        authService.loginUser(username, password, loginCallback);

        // Esperar unos segundos para dar tiempo a la llamada asíncrona de completarse
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(callback.isSuccessful());
    }
    /**
     * Prueba la funcionalidad de actualizar apellidos de usuario.
     */
    @Test
    public void testUpdateSurname_IntegrationWithServer() {

        // Credenciales de usuario de prueba
        String username = "Apu123";
        String password = "B123456-";

        // Arrange
        AuthService authService = new AuthService();

        // Configurar el callback para capturar el resultado
        AuthServiceUnitTest.TestAuthCallback<Void> callback = new AuthServiceUnitTest.TestAuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // La actualización del correo electrónico fue exitosa
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
                token = response.getToken();
                // Llama al método onSuccess de la clase padre para capturar la respuesta
                super.onSuccess(response);
                System.out.println("Login exitoso. Token recibido: " + response.getToken());

                // Datos para la actualización del correo electrónico
                int userId = 13; // ID del usuario
                String newSurname = "ApuuuuApellidos";
                UpdateSurnameRequest updateSurnameRequest = new UpdateSurnameRequest(userId, newSurname, token);

                // Act: Realizar la solicitud de actualización del correo electrónico
                System.out.println("Iniciando prueba de actualización de correo electrónico...");
                authService.updateSurname(userId, updateSurnameRequest, token, callback);
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error si la prueba de inicio de sesión falla
                fail("Error en el login: " + t.getMessage());
            }
        };

        // Act: Iniciar sesión para obtener el token
        System.out.println("Iniciando prueba de login...");
        authService.loginUser(username, password, loginCallback);

        // Esperar unos segundos para dar tiempo a la llamada asíncrona de completarse
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(callback.isSuccessful());
    }

    /**
     * Prueba la funcionalidad de actualizar password de usuario.
     */
    @Test
    public void testUpdatePassword_IntegrationWithServer() {

        // Credenciales de usuario de prueba
        String username = "Apu123";
        String password = "B123456-";

        // Arrange
        AuthService authService = new AuthService();

        // Configurar el callback para capturar el resultado
        AuthServiceUnitTest.TestAuthCallback<Void> callback = new AuthServiceUnitTest.TestAuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // La actualización del correo electrónico fue exitosa
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
                token = response.getToken();
                // Llama al método onSuccess de la clase padre para capturar la respuesta
                super.onSuccess(response);
                System.out.println("Login exitoso. Token recibido: " + response.getToken());

                // Datos para la actualización del correo electrónico
                int userId = 13; // ID del usuario
                String newPassword = "B123456-";
                UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(userId, newPassword, token);

                // Act: Realizar la solicitud de actualización del correo electrónico
                System.out.println("Iniciando prueba de actualización de correo electrónico...");
                authService.updatePassword(userId, updatePasswordRequest, token, callback);
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error si la prueba de inicio de sesión falla
                fail("Error en el login: " + t.getMessage());
            }
        };

        // Act: Iniciar sesión para obtener el token
        System.out.println("Iniciando prueba de login...");
        authService.loginUser(username, password, loginCallback);

        // Esperar unos segundos para dar tiempo a la llamada asíncrona de completarse
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(callback.isSuccessful());
    }

    /**
     * Prueba la funcionalidad de actualizar username de usuario.
     */
    @Test
    public void testUpdateUsername_IntegrationWithServer() {

        // Credenciales de usuario de prueba
        String username = "Apu123";
        String password = "B123456-";

        // Arrange
        AuthService authService = new AuthService();

        // Configurar el callback para capturar el resultado
        AuthServiceUnitTest.TestAuthCallback<Void> callback = new AuthServiceUnitTest.TestAuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // La actualización del correo electrónico fue exitosa
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
                token = response.getToken();
                // Llama al método onSuccess de la clase padre para capturar la respuesta
                super.onSuccess(response);
                System.out.println("Login exitoso. Token recibido: " + response.getToken());

                // Datos para la actualización del correo electrónico
                int userId = 13; // ID del usuario
                String newUsername = "Apu123";
                UpdateUsernameRequest updateUsernameRequest = new UpdateUsernameRequest(userId, newUsername, token);

                // Act: Realizar la solicitud de actualización del correo electrónico
                System.out.println("Iniciando prueba de actualización de correo electrónico...");
                authService.updateUsername(userId, updateUsernameRequest, token, callback);
            }

            @Override
            public void onError(Throwable t) {
                // Manejar el error si la prueba de inicio de sesión falla
                fail("Error en el login: " + t.getMessage());
            }
        };

        // Act: Iniciar sesión para obtener el token
        System.out.println("Iniciando prueba de login...");
        authService.loginUser(username, password, loginCallback);

        // Esperar unos segundos para dar tiempo a la llamada asíncrona de completarse
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(callback.isSuccessful());
    }

    /**
     * Prueba la funcionalidad de recuperar datos de usuario.
     */
    @Test
    public void testGetUserProfile_IntegrationWithServer() {

        // Credenciales de usuario de prueba
        String username = "Apu123";
        String password = "B123456-";

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


}



