package com.example.localmarket.utils;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;
public class AgricultorLocation {
    private LatLng center;
    private int radius;

    // Constructor
    public AgricultorLocation(LatLng center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    // Método para obtener una ubicación aleatoria dentro del radio
    public LatLng getRandomLocation() {
        Random random = new Random();

        // Convertir radio de metros a grados
        double radiusInDegrees = this.radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Ajustar la posición x teniendo en cuenta la longitud
        double new_x = x / Math.cos(Math.toRadians(center.latitude));

        double foundLongitude = center.longitude + new_x;
        double foundLatitude = center.latitude + y;

        return new LatLng(foundLatitude, foundLongitude);
    }
}
