package com.example.localmarket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin, buttonSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignUp);

        // Manejar el evento clic del botón de inicio de sesión
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes realizar la lógica para iniciar sesión, abrir una nueva actividad, etc.
                // Por ejemplo, puedes abrir una nueva actividad:
                // Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                // startActivity(intent);

                // O puedes abrir un fragmento de inicio de sesión:
                // openFragment(new LoginFragment());
            }
        });

        // Manejar el evento clic del botón de registro
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes realizar la lógica para abrir el fragmento de registro
                openFragment(new SignUpfragment());
            }
        });
    }

    // Método para abrir un fragmento en la actividad
    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
