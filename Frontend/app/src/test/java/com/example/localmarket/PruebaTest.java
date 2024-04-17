package com.example.localmarket;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.localmarket.model.LoginRequest;
import com.example.localmarket.model.LoginResponse;
import com.example.localmarket.model.UpdateEmailRequest;
import com.example.localmarket.model.UpdateNameRequest;
import com.example.localmarket.model.UpdatePasswordRequest;
import com.example.localmarket.model.UpdateSurnameRequest;
import com.example.localmarket.model.UpdateUsernameRequest;
import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.TokenManager;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class PruebaTest {

    private AuthService authService;

    @Before
    public void setUp() {
        // Configurar Retrofit para conectarse al servidor real
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kikaikum.ddns.net:3000/localmarket/v1/") // Reemplaza esto con la URL de tu servidor
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una instancia del servicio de la API utilizando Retrofit
        ApiService apiService = retrofit.create(ApiService.class);

        // Inicializar AuthService con el servicio de la API y TokenManager real
        authService = new AuthService(apiService);
    }

    /**
     * Prueba para el método getUserProfile en AuthService
     * Verifica si se obtiene correctamente el perfil de usuario
     */
    @Test
    public void testGetUserProfile_Success() throws InterruptedException {
        // Inicializar un contador de retardo para esperar la respuesta del servidor
        CountDownLatch latch = new CountDownLatch(1);

        // Ejecutar el método getUserProfile
        authService.getUserProfile(3, new AuthService.ProfileCallback() {
            @Override
            public void onSuccess(User userProfile) {
                // Verificar si el usuario devuelto es correcto
                assertEquals("John", userProfile.getName());
                assertEquals("Doe", userProfile.getSurname());
                assertEquals("johndoe", userProfile.getUsername());
                assertEquals("johndoe@example.com", userProfile.getEmail());
                assertEquals("password", userProfile.getPassword());

                // Indicar que la prueba ha finalizado
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                // Indicar que la prueba ha finalizado con un error
                throw new AssertionError("Error: " + t.getMessage());
            }
        });

        // Esperar un máximo de 5 segundos para la respuesta del servidor
        latch.await(5, TimeUnit.SECONDS);
    }

    /**
     * Prueba para el método updateEmail en AuthService
     * Verifica el caso de éxito al actualizar el correo electrónico del usuario
     */
    @Test
    public void testUpdateEmail_Success() throws InterruptedException {

        // Inicializar un contador de retardo para esperar la respuesta del servidor
        CountDownLatch latch = new CountDownLatch(1);

        // Crear una solicitud de actualización de correo electrónico
        UpdateEmailRequest updateEmailRequest = new UpdateEmailRequest(123, "newemail@example.com", "mockedToken");

        // Ejecutar el método updateEmail
        authService.updateEmail(12, updateEmailRequest, "mokedToken", new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // Indicar que la prueba ha finalizado con éxito
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                // Indicar que la prueba ha finalizado con un error
                throw new AssertionError("Error: " + t.getMessage());
            }
        });

        // Esperar un máximo de 5 segundos para la respuesta del servidor
        latch.await(5, TimeUnit.SECONDS);
    }


    // Otras pruebas de actualización de perfil y métodos similares
}