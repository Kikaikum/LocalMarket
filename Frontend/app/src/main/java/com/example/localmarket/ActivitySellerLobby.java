package com.example.localmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivitySellerLobby extends AppCompatActivity {



        private Button miPerfilButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_seller_lobby);

            miPerfilButton = findViewById(R.id.miPerfil);
            miPerfilButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Iniciar la actividad EditProfileActivity
                    Intent intent = new Intent(ActivitySellerLobby.this, EditProfileActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
