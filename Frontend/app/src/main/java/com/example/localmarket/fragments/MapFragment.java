package com.example.localmarket.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localmarket.R;
import com.example.localmarket.model.User;
import com.example.localmarket.network.api.ApiService;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.AgricultorLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private AuthService authService;

    private MapView mapView;
    private GoogleMap gMap;
    private Circle mapCircle;
    private TextView distance;
    private LatLng centerOfCircle;
    private double currentRadius;

    private SeekBar seekBar;
    private static final int MY_LOCATION_REQUEST_CODE = 101; // Código de permiso personalizado
    private List<User> todosLosAgricultores; // Todos los agricultores obtenidos de la base de datos
    private List<User> agricultoresEnRango = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        seekBar= view.findViewById(R.id.seekBar);
        distance= view.findViewById(R.id.tvDistance);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentRadius = progress + 1000;
                distance.setText(currentRadius/1000+" km");
                if (mapCircle != null) {
                    mapCircle.setRadius(currentRadius); // Actualiza el radio del círculo
                }
                filtrarAgricultoresEnRango(currentRadius);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Opcionalmente, aquí puedes añadir código para cuando se empiece a mover la barra
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Opcionalmente, aquí puedes añadir código para cuando se deje de mover la barra
            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setMyLocationButtonEnabled(true);
            gMap.getUiSettings().setZoomControlsEnabled(true); // Mostrar controles de zoom
            gMap.getUiSettings().setScrollGesturesEnabled(true); // Habilitar el desplazamiento del mapa
            gMap.getUiSettings().setRotateGesturesEnabled(true); // Habilitar la rotación del mapa
            gMap.getUiSettings().setTiltGesturesEnabled(true); // Habilitar la inclinación del mapa


            FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            locationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), location -> {
                        if (location != null) {
                            LatLng initialLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            centerOfCircle = initialLocation;
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(location.getLatitude(), location.getLongitude()), 10));
                        }
                        LatLng centerPoint = new LatLng(location.getLatitude(),location.getLongitude());
                        CircleOptions circleOptions = new CircleOptions()
                                .center(centerPoint)
                                .radius(seekBar.getProgress()) // Radio inicial en metros
                                .fillColor(0x55FFC107) // Color azul con transparencia
                                .strokeColor(R.color.accent) // Color del borde
                                .strokeWidth(5); // Grosor del borde

                        mapCircle = gMap.addCircle(circleOptions);
                        //AgricultorLocation generator = new AgricultorLocation(centerPoint, 20000);
                        //cargarAgricultores();


                    });
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }
    }
    private void filtrarAgricultoresEnRango(double rango) {
        agricultoresEnRango = new ArrayList<>();
        for (User user : todosLosAgricultores) {
            LatLng userLocation = new LatLng(user.getLatitud(), user.getLongitud());
            if (SphericalUtil.computeDistanceBetween(centerOfCircle, userLocation) <= currentRadius) {
                agricultoresEnRango.add(user);
            }
        }
    }
    private void cargarAgricultores() {
        authService.getAllUsersAvailable(new AuthService.AuthCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                // Procesar los usuarios: mostrar en el mapa
                mostrarUsuariosEnMapa(users);
            }

            @Override
            public void onError(Throwable error) {
                // Manejar errores aquí, por ejemplo, mostrar un mensaje al usuario
                Log.e("MapFragment", "Error al obtener usuarios: " + error.getMessage());
                Toast.makeText(getContext(), "Error al cargar usuarios: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void mostrarUsuariosEnMapa(List<User> users) {
        if (gMap != null) {
            for (User user : users) {
                if (user.getAgricultor()) {
                    LatLng position = new LatLng(user.getLatitud(), user.getLongitud());
                    gMap.addMarker(new MarkerOptions().position(position).title(user.getName()));
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(gMap); // Llamar a onMapReady manualmente si el permiso es concedido
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
