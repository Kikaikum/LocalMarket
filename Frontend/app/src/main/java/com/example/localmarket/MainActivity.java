package com.example.localmarket;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.localmarket.model.SessionManager;

public class MainActivity extends AppCompatActivity {

    // Aquí puedes declarar otras variables si son necesarias

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa SessionManager o tu método preferido para manejar sesiones
        SessionManager sessionManager = new SessionManager(this);

        // Verifica si hay una sesión iniciada
        if (!sessionManager.isLoggedIn()) {
            // Si no hay sesión, muestra el LoginFragment
            openFragment(new LoginFragment());
        } else if (sessionManager.isLoggedIn() && !sessionManager.isAgricultor()){
            // Si hay una sesión, procede con la actividad principal o muestra otro fragmento
            Intent intent = new Intent(MainActivity.this, ActivityUserLobby.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, ActivitySellerLobby.class);
            startActivity(intent);
        }
    }

    // Método para abrir un fragmento
    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment); // Asegúrate de tener un contenedor en tu layout
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // Si tienes otros métodos, por ejemplo, para manejar back presses o lógica de negocio, irían aquí
}

