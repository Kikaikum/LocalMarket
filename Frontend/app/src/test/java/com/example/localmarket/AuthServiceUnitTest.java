package com.example.localmarket;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.net.ConnectException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import retrofit2.HttpException;


/**
 * Pruebas unitarias para AuthService.
 * Estas pruebas verifican el comportamiento de los métodos en AuthService.
 *
 * @author Oriol Estero Sanchez
 */

public class AuthServiceUnitTest {

    private static int randomNumber = 1;
    private static final Random random = new Random();

    @Mock
    private ApiService apiService;
    private AuthService authService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Prueba el inicio de sesión de un usuario con éxito.
     *
     * Este método verifica si el inicio de sesión de un usuario funciona correctamente.
     * Se espera que el método de autenticación llame al método onSuccess del callback si el inicio de sesión es exitoso.
     * En caso de error, el método falla y muestra un mensaje de error.
     */

    @Test
    public void testLoginUser_Success() {

        // Arrange
        AuthService authService = new AuthService();
        String userName = "testUser";
        String password = "Testuser!!";

        // Configuración del callback para capturar el resultado
        TestAuthCallback<LoginResponse> callback = new TestAuthCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
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
     * Prueba el inicio de sesión de un usuario que falla debido a credenciales incorrectas.
     *
     * Este método verifica si el inicio de sesión de un usuario falla correctamente cuando se proporcionan credenciales incorrectas.
     * Se espera que el método de autenticación llame al método onError del callback con un error de "Unauthorized" (401).
     */
    @Test
    public void testLoginUser_Failure() {

        AuthService authService = new AuthService();
        String username = "testUser";
        String password = "Testuser!";
        CountDownLatch latch = new CountDownLatch(1);
        final boolean[] connectionFailed = {false}; // Variable para rastrear si la conexión falló

        AuthService.AuthCallback<LoginResponse> callback = new TestAuthCallback<LoginResponse>() {
            @Override
            public void onError(Throwable t) {
                if (t instanceof HttpException) {
                    HttpException httpException = (HttpException) t;
                    if (httpException.code() == 401) {

                        System.out.println("Error: Unauthorized - Las credenciales son incorrectas.");
                        assertTrue(true);
                        latch.countDown();
                        return;
                    }
                }
                if (t instanceof ConnectException) {
                    // Manejar el caso en el que no se obtiene respuesta del servidor
                    System.out.println("Error de conexión: " + t);
                    connectionFailed[0] = true; // Establecer que la conexión falló
                    latch.countDown();
                }
                // Otros casos de error
            }
        };

        // Act
        authService.loginUser(username, password, callback);

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertFalse(connectionFailed[0]);
    }

    /**
     * Prueba el registro de un nuevo usuario con éxito.
     *
     * Este método verifica si el registro de un nuevo usuario funciona correctamente.
     * Se espera que el método de autenticación llame al método onSuccess del callback si el registro es exitoso.
     */
    @Test
    public void testSignUpUser_Success() {
        // Arrange
        AuthService authService = new AuthService();
        String email = generateUniqueEmail();
        String password = "Newpassword!!";
        String username = generateUniqueUsername();
        String name = "New";
        String surname = "User";
        boolean isVendor = true; // Corregir el nombre de la variable
        TestAuthCallback<SignUpResponse> callback = new TestAuthCallback<SignUpResponse>(){
            @Override
            public void onSuccess(SignUpResponse response) {
                super.onSuccess(response);
            }
        };

        // Act
        authService.signUpUser(email, password, username, name, surname, isVendor, callback);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Assert
        assertTrue(callback.isSuccessful());

    }

    /**
     * Prueba el registro de un nuevo usuario que falla debido a un conflicto de datos duplicados.
     *
     * Este método verifica si el registro de un nuevo usuario falla correctamente cuando se intenta registrar con datos que ya existen.
     * Se espera que el método de autenticación llame al método onError del callback con un error de "Conflict" (409).
     */
    @Test
    public void testSignUpUser_Failure() {
        // Arrange
        AuthService authService = new AuthService();
        String email = "test@test.com";
        String password = "TestUser!!";
        String username = "testUser";
        String name = "asd";
        String surname = "asd";
        boolean isVendor = false;

        CountDownLatch latch = new CountDownLatch(1);
        final boolean[] uniqueConstraintErrorOccurred = {false};
        TestAuthCallback<SignUpResponse> callback = new TestAuthCallback<SignUpResponse>() {
            @Override
            public void onError(Throwable t) {
                if (t instanceof HttpException) {
                    HttpException httpException = (HttpException) t;
                    if (httpException.code() == 409) {
                        System.out.println("Error de conflicto: " + t.getMessage());
                        assertTrue(true);
                        latch.countDown();
                        return;
                    }
                }
                if (t instanceof ConnectException) {
                    System.out.println("Error de conexión: " + t);
                    uniqueConstraintErrorOccurred[0] = true; // Establecer que la conexión falló
                    latch.countDown();
                }

            }
        };

        // Act
        authService.signUpUser(email, password, username, name, surname, isVendor, callback);
        try {
            latch.await(3,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Assert
        assertFalse(uniqueConstraintErrorOccurred[0]);
    }

    /**
     * Prueba el cierre de sesión de un usuario.
     *
     * Este método verifica si el cierre de sesión de un usuario funciona correctamente.
     * Se espera que el método de autenticación llame al método onSuccess del callback para indicar que el usuario se ha desconectado correctamente.
     */
    @Test
    public void testLogoutUser() {
        // Arrange
        AuthService authService = new AuthService();
        User currentUser = new User("oriol", "estero", "testUser", "oriolestero@gmail.io", "TestUser!!", 7, false);
        authService.setCurrentUser(currentUser);
        TestAuthCallback<Void> callback = new TestAuthCallback<>();

        // Act
        authService.logoutUser(callback);

        // Assert
        assertTrue(callback.isSuccessful());

    }


    // Clase de prueba para el callback de autenticación
    static class TestAuthCallback<T> implements AuthService.AuthCallback<T> {
        private boolean successful;
        private T response;

        @Override
        public void onSuccess(T response) {
            this.successful = true; // Se marca como exitoso
            this.response = response;
        }

        @Override
        public void onError(Throwable t) {
            this.successful = false;
            // Manejo del error según sea necesario
        }
        public  void setSuccessful(){
            this.successful=true;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public T getResponse() {
            return response;
        }

        public void setResponse(T response) {
            this.response = response;
        }
    }

    public class ServerOfflineException extends RuntimeException {
        public ServerOfflineException() {
            super("Server is offline");
        }
    }
    private String generateUniqueUsername() {
        // Concatenar un prefijo con un número único (puede ser el valor actual de userCount)
        randomNumber = random.nextInt(90000) + 10000;
        return "user" + randomNumber;
    }

    // Método para generar un correo electrónico único concatenado con un número
    private String generateUniqueEmail() {
        // Concatenar un prefijo con un número único (puede ser el valor actual de userCount)
        randomNumber = random.nextInt(90000) + 10000;
        return "user" + randomNumber + "@example.com";
    }
}

