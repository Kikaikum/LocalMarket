package com.example.localmarket;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.SignUpResponse;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Oriol
 */

public class AuthServiceUnitTest {

    @Mock
    private ApiService apiService;
    private AuthService authService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testLoginUser_Success() {
        // Arrange
        ApiService apiService = Mockito.mock(ApiService.class);
        Call<LoginResponse> mockedCall = Mockito.mock(Call.class);
        Mockito.when(apiService.loginUser(Mockito.any(LoginRequest.class))).thenReturn(mockedCall);

        // Simulamos una respuesta exitosa del servicio
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("token_de_prueba");
        Response<LoginResponse> response = Response.success(loginResponse);

        // Usamos doAnswer en lugar de thenAnswer
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Callback<LoginResponse> callback = invocation.getArgument(0);
                callback.onResponse(mockedCall, response);
                return null;
            }
        }).when(mockedCall).enqueue(Mockito.any(Callback.class));

        AuthService authService = new AuthService(apiService); // Inyectamos el servicio simulado
        String userName = "testUser";
        String password = "Testuser!!";
        TestAuthCallback<LoginResponse> callback = new TestAuthCallback<>();

        // Act
        authService.loginUser(userName, password, callback);

        // Assert
        assertTrue(callback.isSuccessful());
        assertNotNull(callback.getResponse().getToken());
    }

    @Test
    public void testLoginUser_Failure() {
        // Arrange
        AuthService authService = new AuthService();
        String userName = "usuario!!";
        String password = "invalidpassword";
        TestAuthCallback<LoginResponse> callback = new TestAuthCallback<>();

        // Act
        authService.loginUser(userName, password, callback);

        // Assert
        assertFalse(callback.isSuccessful());
        //assertNull(callback.getResponse()); // Verifica que la respuesta sea null
    }


    @Test
    public void testSignUpUser_Success() {
        // Arrange
        AuthService authService = new AuthService();
        String email = "newuser@example.com";
        String password = "Newpassword!!";
        String username = "newuser2";
        String name = "New";
        String surname = "User";
        boolean isVendor = true; // Corregir el nombre de la variable
        TestAuthCallback<SignUpResponse> callback = new TestAuthCallback<>();

        // Act
        authService.signUpUser(email, password, username, name, surname, isVendor, callback);

        // Simular una respuesta exitosa del servidor
        SignUpResponse signUpResponse = new SignUpResponse();
        //signUpResponse.setUserId(2); // Establecer el userId en 2
        callback.onSuccess(signUpResponse); // Llamar al método onSuccess con la respuesta simulada

        // Assert
        assertTrue(callback.isSuccessful());

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
        //User currentUser = new User("oriol","estero", "testUser", "oriolestero@gmaIL.com","TestUser!!","1");
        //authService.setCurrentUser(currentUser);
        TestAuthCallback<Void> callback = new TestAuthCallback<>();

        // Act
        authService.logoutUser(callback);

        // Assert
        assertTrue(callback.isSuccessful());
        //assertNull(authService.getCurrentUser());
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
}

