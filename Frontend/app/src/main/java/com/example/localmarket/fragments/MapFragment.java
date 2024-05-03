package com.example.localmarket.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.localmarket.R;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;
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
    //cambis
    private MapView mapView;
    private GoogleMap gMap;
    private Circle mapCircle;
    private TextView distance;
    private LatLng centerOfCircle;
    private double currentRadius;
    private double latitude;
    private double longitude;


    private SeekBar seekBar;
    private static final int MY_LOCATION_REQUEST_CODE = 101;
    private final List<User> todosLosAgricultores = new ArrayList<>();
    private final List<User> agricultoresEnRango = new ArrayList<>();
    UserProductFragment userProductFragment;
    private android.widget.Toast Toast;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        authService = new AuthService();
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        seekBar = view.findViewById(R.id.seekBar);
        distance = view.findViewById(R.id.tvDistance);

        // Agregamos el listener al botón "actualizarBusqueda"
        view.findViewById(R.id.actualizarBusqueda).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catchAgricultorOnRange();
                backToUserProductFragment();
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Volver al fragmento anterior
                getParentFragmentManager().popBackStack();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentRadius = progress + 1000;
                distance.setText(currentRadius / 1000 + " km");
                if (mapCircle != null) {
                    mapCircle.setRadius(currentRadius);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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
            gMap.getUiSettings().setZoomControlsEnabled(true);
            gMap.getUiSettings().setScrollGesturesEnabled(true);
            gMap.getUiSettings().setRotateGesturesEnabled(true);
            gMap.getUiSettings().setTiltGesturesEnabled(true);

            FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            locationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), location -> {
                        if (location != null) {
                            LatLng initialLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            centerOfCircle = initialLocation;
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10));
                        }
                        LatLng centerPoint = new LatLng(location.getLatitude(), location.getLongitude());
                        CircleOptions circleOptions = new CircleOptions()
                                .center(centerPoint)
                                .radius(seekBar.getProgress())
                                .fillColor(0x55FFC107)
                                .strokeColor(R.color.accent)
                                .strokeWidth(5);

                        mapCircle = gMap.addCircle(circleOptions);


                        sendLocationAndFetchAgricultores(location.getLatitude(),location.getLongitude());


                    });

        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }

    }

    private void catchAgricultorOnRange() {
        agricultoresEnRango.clear();
        if (centerOfCircle != null) {
            for (User user : todosLosAgricultores) {
                double distanceToUser = SphericalUtil.computeDistanceBetween(new LatLng(user.getLatitud(), user.getLongitud()), centerOfCircle);
                if (distanceToUser <= currentRadius) {
                    agricultoresEnRango.add(user);
                    Log.e("Agricultor", "Id: " + user.getId() + "Nombre: " + user.getName() + " " + user.getSurname() + ", Distancia: " + distanceToUser + " metros");
                }
            }
            sendAgricultoresToUserProductFragment();

        }
    }

    private void sendAgricultoresToUserProductFragment() {

        Bundle bundle = new Bundle();
        ArrayList<Integer> agricultoresIds = new ArrayList<>();
        for (User user : agricultoresEnRango) {
            agricultoresIds.add(user.getId());
        }
        bundle.putIntegerArrayList("agricultoresIds", agricultoresIds);

        userProductFragment = UserProductFragment.newInstance();
        userProductFragment.setArguments(bundle);


    }
    private void sendLocationAndFetchAgricultores(double latitude, double longitude) {
        // Enviar la ubicación al servidor y recibir la lista de usuarios en rango

        authService.getUsersInRange(latitude, longitude, new AuthService.AuthCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> usersInRange) {
                Log.e("log user2", usersInRange.toString().trim());
                // Procesar la lista de usuarios en rango
                for (User user : usersInRange) {
                    todosLosAgricultores.add(user);
                    Log.e("log User", user.getName());
                    showUsersOnMap(usersInRange);
                    catchAgricultorOnRange();
                }
            }

            @Override
            public void onError(Throwable t) {
                // Manejar errores al obtener la lista de usuarios en rango
                Toast.makeText(getActivity(), "Error al obtener usuarios en rango :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("error", t.getMessage());
            }
        });
    }

    private void showUsersOnMap(List<User> usersInRange) {

        for (User user : usersInRange){
            gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(user.getLatitud(), user.getLongitud()))
                    .title(user.getName() + " " + user.getSurname()));

        }

    }

    private void backToUserProductFragment() {

        // Reemplazar o agregar UserProductFragment en la actividad principal
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.containerUserLobby, userProductFragment);
        transaction.commit();
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