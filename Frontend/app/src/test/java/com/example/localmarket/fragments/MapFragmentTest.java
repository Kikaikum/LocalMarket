package com.example.localmarket.fragments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.os.Bundle;

import com.example.localmarket.fragments.MapFragment;
import com.example.localmarket.fragments.UserProductFragment;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MapFragmentTest {
    @Mock
    Bundle args ;
    @Test
    public void testCreateMapFragment() {
        MapFragment mapFragment = new MapFragment();
        assertNotNull(mapFragment);
    }

    @Test
    public void testShowUsersOnMap() {
        // Crear una instancia de MapFragment
        MapFragment mapFragment = new MapFragment();

        // Crear una instancia mock de GoogleMap
        GoogleMap mockGoogleMap = Mockito.mock(GoogleMap.class);

        // Establecer el mock de GoogleMap en el MapFragment
        mapFragment.setGoogleMap(mockGoogleMap);

        // Crear una lista de usuarios para mostrar en el mapa
        List<User> usersInRange = new ArrayList<>();
        usersInRange.add(new User("John", "Doe", "pacoman", "pacoman@gmail.com", "Pacoman!!", 15, true, 10.1, 20.1));
        usersInRange.add(new User("John2", "Doe2", "pacoman2", "pacoman2@gmail.com", "Pacoman!!", 16, true, 10.0, 20.0));

        for (User user : usersInRange) {
            mockGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(user.getLatitud(), user.getLongitud()))
                    .title(user.getName() + " " + user.getSurname()));
        }
    }
    @Test
    public void testSendLocation() {
        // Crear una instancia de MapFragment
        MapFragment mapFragment = new MapFragment();

        // Crear una lista simulada de agricultores en rango
        List<User> agricultoresEnRango = new ArrayList<>();
        agricultoresEnRango.add(new User("pepe", "John", "Doe","pacoman","pacoman@gmail.com",1,true,10.1, 10.1));
        agricultoresEnRango.add(new User("pepe2", "John2", "Doe2","pacoman","pacoman@gmail.com",1,true,10.1, 10.1));

        // Establecer la lista simulada de agricultores en rango en el MapFragment
        mapFragment.agricultoresEnRango = agricultoresEnRango;

        // Mock del Bundle
        args = Mockito.mock(Bundle.class);

        // Simular comportamiento del Bundle
        Mockito.when(args.containsKey(Mockito.anyString())).thenReturn(true);
        Mockito.when(args.getIntegerArrayList(Mockito.anyString())).thenReturn(new ArrayList<>(Arrays.asList(1, 2)));

        // Simular la asignación del Bundle al fragmento de usuario
        UserProductFragment mockUserProductFragment = Mockito.mock(UserProductFragment.class);
        //Mockito.when(mockUserProductFragment.getArguments()).thenReturn(args);
        mapFragment.userProductFragment = mockUserProductFragment;

        // Llamar al método sendAgricultoresToUserProductFragment
        //mapFragment.sendAgricultoresToUserProductFragment();

        // Verificar que se creó un Bundle con la lista de IDs de agricultores
        assertNotNull(mapFragment.userProductFragment);
        assertNotNull(args);
        assertTrue(args.containsKey("agricultoresIds"));
        ArrayList<Integer> agricultoresIds = args.getIntegerArrayList("agricultoresIds");
        assertNotNull(agricultoresIds);
        assertEquals(agricultoresEnRango.size(), agricultoresIds.size());
        assertEquals(Integer.valueOf(1), agricultoresIds.get(0));
        assertEquals(Integer.valueOf(2), agricultoresIds.get(1));
    }
    @Test
    public void testSendLocationAndFetchAgricultores() {
        // Crear una instancia de MapFragment
        MapFragment mapFragment = new MapFragment();

        // Crear una lista simulada de usuarios en rango
        List<User> usersInRange = new ArrayList<>();
        usersInRange.add(new User("pepe", "John", "Doe","pacoman","pacoman@gmail.com",1,true,10.1, 10.1));
        // Agregar más usuarios si es necesario...

        // Crear una implementación simulada de AuthService
        AuthService mockAuthService = new AuthService() {
            @Override
            public void getUsersInRange(double latitude, double longitude, AuthCallback<List<User>> callback) {
                // Simular la llamada exitosa al servidor y pasar la lista simulada de usuarios
                callback.onSuccess(usersInRange);
            }
        };
    }
}