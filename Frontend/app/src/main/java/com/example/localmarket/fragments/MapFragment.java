package com.example.localmarket.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.localmarket.R;
import com.example.localmarket.model.User;
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

    private MapView mapView;
    private GoogleMap gMap;
    private Circle mapCircle;
    private TextView distance;

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
                int adjustedProgress = progress + 1000;
                distance.setText(adjustedProgress/1000+" km");
                if (mapCircle != null) {
                    mapCircle.setRadius(adjustedProgress); // Actualiza el radio del círculo
                }
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
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(location.getLatitude(), location.getLongitude()), 12));
                        }
                        LatLng centerPoint = new LatLng(location.getLatitude(),location.getLongitude());
                        CircleOptions circleOptions = new CircleOptions()
                                .center(centerPoint)
                                .radius(seekBar.getProgress()) // Radio inicial en metros
                                .fillColor(0x55FFC107) // Color azul con transparencia
                                .strokeColor(R.color.accent) // Color del borde
                                .strokeWidth(5); // Grosor del borde

                        mapCircle = gMap.addCircle(circleOptions);
                        AgricultorLocation generator = new AgricultorLocation(centerPoint, 20000);
                        for (int i = 0; i < 10; i++) { // Generar y agregar 10 marcadores
                            LatLng randomPoint = generator.getRandomLocation(); // Obtener ubicación aleatoria
                            gMap.addMarker(new MarkerOptions().position(randomPoint).title("Agricultor " + (i + 1)));
                        }


                    });
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }
    }
    private void filtrarAgricultoresEnRango(double rango) {
        if (todosLosAgricultores == null) return;
        agricultoresEnRango.clear();
        LatLng center = new LatLng(gMap.getCameraPosition().target.latitude, gMap.getCameraPosition().target.longitude);

        for (User agricultor : todosLosAgricultores) {
            LatLng posAgricultor = new LatLng(agricultor.getLatitud(), agricultor.getLongitud());
            if (SphericalUtil.computeDistanceBetween(center, posAgricultor) <= rango) {
                agricultoresEnRango.add(agricultor);
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
