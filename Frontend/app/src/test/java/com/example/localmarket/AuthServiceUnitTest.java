package com.example.localmarket;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.network.service.AuthService;
import com.google.firebase.firestore.auth.User;

import org.junit.Test;

public class AuthServiceUnitTest {

    @Test
    public void testLoginUser_Success() {
        // Arrange
        AuthService authService = new AuthService();
        String email = "test@example.com";
        String password = "password";
        TestAuthCallback<LoginResponse> callback = new TestAuthCallback<>();

        // Act
        authService.loginUser(email, password, callback);

        // Assert
        //assertTrue(callback.isSuccessful());
        assertNotNull(callback.getResponse().getToken());
        //assertEquals(1, callback.getResponse().getUserId());
    }

    @Test
    public void testLoginUser_Failure() {
        // Arrange
        AuthService authService = new AuthService();
        String email = "invalid@example.com";
        String password = "invalidpassword";
        TestAuthCallback<LoginResponse> callback = new TestAuthCallback<>();

        // Act
        authService.loginUser(email, password, callback);

        // Assert
        assertFalse(callback.isSuccessful());
        assertNull(callback.getResponse().getToken());
        //assertNull(callback.getResponse().getUserId());
    }

    @Test
    public void testSignUpUser_Success() {
        // Arrange
        AuthService authService = new AuthService();
        String email = "newuser@example.com";
        String password = "newpassword";
        String username = "newuser";
        String name = "New";
        String surname = "User";
        boolean isvendor =true;
        TestAuthCallback<SignUpResponse> callback = new TestAuthCallback<>();

        // Act
        authService.signUpUser(email, password, username, name, surname,isvendor,callback);

        // Assert
        assertTrue(callback.isSuccessful());
        //assertNotNull(callback.getResponse().getToken());
        assertEquals(2, callback.getResponse().getUserId());
    }

    @Test
    public void testSignUpUser_Failure() {
        // Arrange
        AuthService authService = new AuthService();
        String email = "existinguser@example.com";
        String password = "existingpassword";
        String username = "existinguser";
        String name = "Existing";
        String surname = "User";
        boolean agricultor = true;
        TestAuthCallback<SignUpResponse> callback = new TestAuthCallback<>();

        // Act
        authService.signUpUser(email, password, username, name, surname, agricultor, callback);

        // Assert
        assertFalse(callback.isSuccessful());
        //assertNull(callback.getResponse().getToken());
        //assertNull(callback.getResponse().getUserId());
    }

    @Test
    public void testLogoutUser() {
        // Arrange
        AuthService authService = new AuthService();
        // Assuming user is already logged in
        User currentUser = new User("test");
        //authService.setCurrentUser(currentUser);
        TestAuthCallback<Void> callback = new TestAuthCallback<>();

        // Act
        authService.logoutUser(callback);

        // Assert
        assertTrue(callback.isSuccessful());
        //assertNull(authService.getCurrentUser());
    }

    // Clase de prueba para el callback de autenticación
    private static class TestAuthCallback<T> implements AuthService.AuthCallback<T> {
        private boolean successful;
        private T response;

        @Override
        public void onSuccess(T response) {
            this.successful = true;
            this.response = response;
        }

        @Override
        public void onError(Throwable t) {
            this.successful = false;
            // Manejo del error según sea necesario
        }

        public boolean isSuccessful() {
            return successful;
        }

        public T getResponse() {
            return response;
        }
    }
}

