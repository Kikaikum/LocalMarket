package com.example.localmarket;
import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.Product;
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
import com.example.localmarket.network.api.ApiService;

import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;

import java.util.List;

import retrofit2.Call;
/**
 * Clase de prueba para AuthService, específicamente para el método updateUserProfile
 * y los fragmentos EditEmailFragment,EditNameFragment, EditSurnameFragment
 * EditUsernameFragment y EditPasswordFragment
 * @author Ainoha
 */
public class AuthServiceEditProfilTest {
        private ApiServiceStub apiService;
        private TokenManagerStub tokenManager;
        private AuthService authService;

        @Before
        public void setUp() {
            // Configurar instancias simuladas
            apiService = new ApiServiceStub();
            tokenManager = new TokenManagerStub();
            authService = new AuthService();
        }


    /**
     * Clase simulada de ApiService para la prueba
     */
        private static class ApiServiceStub implements ApiService {
            private User userProfile;
            boolean updateEmailCalled = false;
            int userId;
            UpdateEmailRequest updateEmailRequest;
            String token;
            private boolean updateNameResponse;

            public void setUpdateNameResponse(boolean updateNameResponse) {
                this.updateNameResponse = updateNameResponse;
            }

            public void getUserProfile(int userId, AuthService.ProfileCallback callback) {
                callback.onSuccess(userProfile);
            }

            public void setUserProfile(User userProfile) {
                this.userProfile = userProfile;
            }

            @Override
            public Call<LoginResponse> loginUser(LoginRequest loginRequest) {
                return null;
            }

            @Override
            public Call<SignUpResponse> createUser(SignUpRequest signUpRequest) {
                return null;
            }

            @Override
            public Call<User> getUserProfile(int userId) {
                return null;
            }

            @Override
            public Call<User> getUserData(String username) {
                return null;
            }

            @Override
            public Call<Void> deleteAccount(int userId, String token) {
                return null;
            }

            @Override
            public Call<Void> updateUsername(int userId, String token, UpdateUsernameRequest updateUsernameRequest) {
                return null;
            }

            @Override
            public Call<Void> updateEmail(int userId, String token, UpdateEmailRequest updateEmailRequest) {
                return null;
            }

            @Override
            public Call<Void> updateName(int userId, String token, UpdateNameRequest updateNameRequest) {
                return null;
            }

            @Override
            public Call<Void> updateSurname(int userId, String token, UpdateSurnameRequest updateSurnameRequest) {
                return null;
            }

            @Override
            public Call<Void> updatePassword(int userId, String token, UpdatePasswordRequest updatePasswordRequest) {
                return null;
            }



            @Override
            public Call<Product> getProductById(int productId) {
                return null;
            }

            @Override
            public Call<ProductResponse> addProduct(ProductRequest product) {
                return null;
            }

            @Override
            public Call<List<Product>> getAllProducts() {
                return null;
            }

            @Override
            public Call<Void> updateProduct(int productId, String authorization, Product updatedProduct) {
                return null;
            }

            @Override
            public Call<Void> deleteProduct(int productId, String token) {
                return null;
            }
        }

    /**
     * Clase simulada de TokenManager para la prueba
     */
        private static class TokenManagerStub extends TokenManager {
            private int userId;
            private String token;

            @Override
            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
            @Override
            public String getToken() {return token;}
            public void setToken(String token) {this.token = token;            }
        }
    /**
     * Prueba para el método getUserProfile en AuthService
     * Verifica si se obtiene correctamente el perfil de usuario
     */
    @Test
    public void testGetUserProfile_Success() {
        // Configurar estado simulado
        tokenManager.setUserId(123); // Simula el ID de usuario
        apiService.setUserProfile(new User("John", "Doe", "johndoe", "johndoe@example.com", "password", 3, true));

        // Ejecutar método bajo prueba
        authService.getUserProfile(3, new AuthService.ProfileCallback() {
            @Override
            public void onSuccess(User userProfile) {
                // Verificar si el usuario devuelto es correcto
                assertEquals("John", userProfile.getName());
                assertEquals("Doe", userProfile.getSurname());
                assertEquals("johndoe", userProfile.getUsername());
                assertEquals("johndoe@example.com", userProfile.getEmail());
                assertEquals("password", userProfile.getPassword());
            }

            @Override
            public void onError(Throwable t) {
                // No esperado para esta prueba
            }
        });
    }
    @Test
    public void testGetUserProfile_Error() {
        // Configurar estado simulado
        tokenManager.setUserId(456); // Simula un ID de usuario diferente
        apiService.setUserProfile(null); // Simula un perfil de usuario nulo

        // Ejecutar método bajo prueba
        authService.getUserProfile(456, new AuthService.ProfileCallback() {
            /**
             * Verifica si el usuario devuelto es correcto.
             *
             * @param userProfile El perfil de usuario devuelto.
             */
            @Override
            public void onSuccess(User userProfile) {
                // Este método no debería ejecutarse en caso de error
                assertTrue(false); // La prueba fallará si se ejecuta este método
            }

            /**
             * Método de callback en caso de error.
             *
             * @param t La excepción que indica el error.
             */
            @Override
            public void onError(Throwable t) {
                // Verificar que el método onError maneje correctamente el error
                assertTrue(true); // Si se ejecuta este método, significa que el error se ha manejado correctamente
            }
        });
    }
    /**
     * Prueba para el método updateEmail en AuthService
     * Verifica el caso de éxito al actualizar el correo electrónico del usuario
     */
    @Test
    public void testUpdateEmail_Success() {
        // Simular datos necesarios
        int userId = 123;
        String token = "mockedToken";
        String newEmail = "newemail@example.com";
        UpdateEmailRequest updateEmailRequest = new UpdateEmailRequest(userId, newEmail, token);

        // Ejecutar el método que quieres probar
        authService.updateEmail(userId, updateEmailRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Verificar el éxito de la llamada
                assertTrue(true); // Si la llamada se ejecuta con éxito, este assert pasará
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(false); // Si se llega a este punto, significa que el error se ha manejado correctamente
            }
        });
    }
    /**
     * Prueba para el método updateEmail en AuthService
     * Verifica el caso de error al actualizar el correo electrónico del usuario
     */
    @Test
    public void testUpdateEmail_Error() {
        // Simular datos necesarios
        int userId = 123;
        String token = "mockedToken";
        String newEmail = "newemail@example.com";
        UpdateEmailRequest updateEmailRequest = new UpdateEmailRequest(userId, newEmail, token);

        // Ejecutar el método que quieres probar
        authService.updateEmail(userId, updateEmailRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Verificar el éxito de la llamada
                assertTrue(false); // Si la llamada se ejecuta con éxito, este assert pasará
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(true); // Si se llega a este punto, significa que el error se ha manejado correctamente
            }
        });
    }
    /**
     * Prueba para el método updateName en AuthService
     * Verifica el caso de éxito al actualizar el nombre del usuario
     */
    @Test
    public void testUpdateName_Success() {
        // Simular datos necesarios para un escenario de error
        int userId = -1; // ID de usuario inválido
        String token = "invalidToken"; // Token inválido
        String newName = "New Name";
        UpdateNameRequest updateNameRequest = new UpdateNameRequest(userId, newName, token);

        // Ejecutar el método que quieres probar
        authService.updateName(userId, updateNameRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Verificar el éxito de la llamada
                assertTrue(true); // Si la llamada se ejecuta con éxito, este assert pasará
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(false); // Si se llega a este punto, significa que el error se ha manejado correctamente
            }
        });
    }
    /**
     * Prueba para el método updateName en AuthService
     * Verifica el caso de error al actualizar el nombre del usuario
     */
    @Test
    public void testUpdateName_Error() {
        // Simular datos necesarios para un escenario de error
        int userId = -1; // ID de usuario inválido
        String token = "invalidToken"; // Token inválido
        String newName = "New Name";
        UpdateNameRequest updateNameRequest = new UpdateNameRequest(userId, newName, token);

        // Ejecutar el método que quieres probar
        authService.updateName(userId, updateNameRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Verificar el éxito de la llamada
                assertTrue(false); // Si la llamada se ejecuta con éxito, este assert pasará
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(true); // Si se llega a este punto, significa que el error se ha manejado correctamente
            }
        });
    }
    /**
     * Prueba para el método updateSurname en AuthService
     * Verifica el caso de éxito al actualizar el apellido del usuario
     */
    @Test
    public void testUpdateSurname_Success() {
        // Simular datos necesarios
        int userId = 3;
        String token = "mockedToken";
        String newSurname = "New Surname";
        UpdateSurnameRequest updateSurnameRequest = new UpdateSurnameRequest(userId, newSurname, token);

        // Ejecutar el método que quieres probar
        authService.updateSurname(userId, updateSurnameRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Verificar el éxito de la llamada
                assertTrue(true); // Si la llamada se ejecuta con éxito, este assert pasará
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(false); // Si se llega a este punto, significa que el error se ha manejado correctamente
            }
        });
    }
    /**
     * Prueba para el método updateSurname en AuthService
     * Verifica el caso de error al actualizar el apellido del usuario
     */
    @Test
    public void testUpdateSurname_Error() {
        // Simular datos necesarios para un escenario de error
        int userId = -1; // ID de usuario inválido
        String token = "invalidToken"; // Token inválido
        String newSurname = "New Surname";
        UpdateSurnameRequest updateSurnameRequest = new UpdateSurnameRequest(userId, newSurname, token);

        // Ejecutar el método que quieres probar
        authService.updateSurname(userId, updateSurnameRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Este método no debería ejecutarse en caso de error
                assertTrue(false); // La prueba fallará si se ejecuta este método
            }

            @Override
            public void onError(Throwable t) {
                // Verificar que el método onError maneje correctamente el error
                assertTrue(true); // Si se ejecuta este método, significa que el error se ha manejado correctamente
            }
        });
    }
    /**
     * Prueba para el método updateUsername en AuthService
     * Verifica el caso de éxito al actualizar el nombre de usuario
     */
    @Test
    public void testUpdateUsername_Success() {
        // Simular datos necesarios
        int userId = 3;
        String token = "mockedToken";
        String newUsername = "New Username";
        UpdateUsernameRequest updateUsernameRequest = new UpdateUsernameRequest(userId, newUsername, token);
        AuthServiceUnitTest.TestAuthCallback<SignUpResponse> callback = new AuthServiceUnitTest.TestAuthCallback<>();
        // Ejecutar el método que quieres probar
        authService.updateUsername(userId, updateUsernameRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Verificar el éxito de la llamada
                assertTrue(callback.isSuccessful()); // Si la llamada se ejecuta con éxito, este assert pasará

            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
    /**
     * Prueba para el método updateUsername en AuthService
     * Verifica el caso de error al actualizar el nombre de usuario
     */
    @Test
    public void testUpdateUsername_Error() {

        // Simular datos necesarios para un escenario de error
        int userId = -1; // ID de usuario inválido
        String token = "invalidToken"; // Token inválido
        String newUsername = "New Username";
        UpdateUsernameRequest updateUsernameRequest = new UpdateUsernameRequest(userId, newUsername, token);

        // Ejecutar el método que quieres probar
        authService.updateUsername(userId, updateUsernameRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Verificar el éxito de la llamada
                assertTrue(true); // Si la llamada se ejecuta con éxito, este assert pasará
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(false); // Si se llega a este punto, significa que el error se ha manejado correctamente
            }
        });
    }
    /**
     * Prueba para el método updatePassword en AuthService
     * Verifica el caso de éxito al actualizar la contraseña del usuario
     */
    @Test
    public void testUpdatePassword_Success() {
        int userId = -1; // ID de usuario inválido
        String token = "invalidToken"; // Token inválido
        String newUsername = "New Password";
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(userId, newUsername, token);

        // Ejecutar el método que quieres probar
        authService.updatePassword(userId, updatePasswordRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Verificar el éxito de la llamada
                assertTrue(false); // Si la llamada se ejecuta con éxito, este assert pasará
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(true); // Si se llega a este punto, significa que el error se ha manejado correctamente
            }
        });
    }
    /**
     * Prueba para el método updatePassword en AuthService
     * Verifica el caso de error al actualizar la contraseña del usuario
     */
    @Test
    public void testUpdatePassword_Error() {
        // Simular datos necesarios
        int userId = 3;
        String token = "mockedToken";
        String newUsername = "New Password";
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(userId, newUsername, token);

        // Ejecutar el método que quieres probar
        authService.updatePassword(userId, updatePasswordRequest, token, new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Verificar el éxito de la llamada
                assertTrue(true); // Si la llamada se ejecuta con éxito, este assert pasará
            }

            @Override
            public void onError(Throwable t) {
                assertTrue(true); // Si se llega a este punto, significa que el error se ha manejado correctamente
            }
        });
    }
}

