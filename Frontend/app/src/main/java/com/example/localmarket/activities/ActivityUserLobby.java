package com.example.localmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localmarket.R;
import com.example.localmarket.fragments.MapFragment;
import com.example.localmarket.fragments.UserProductFragment;
import com.example.localmarket.model.Product;
import com.example.localmarket.model.User;
import com.example.localmarket.network.service.AuthService;
import com.example.localmarket.utils.OnAgricultoresInRangeListener;
import com.example.localmarket.utils.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Actividad que representa el lobby del usuario.
 * Muestra una lista de productos para que el usuario los explore y proporciona opciones de perfil y cierre de sesión.
 * Esta actividad está diseñada para usuarios normales que están explorando productos.
 * No tiene funcionalidades avanzadas como agregar, editar o eliminar productos.
 *
 * @author Oriol Estero Sanchez
 */
public class ActivityUserLobby extends AppCompatActivity implements OnAgricultoresInRangeListener {

    private static final int FRAGMENT_USER_PRODUCTS = 0;
    private static final int FRAGMENT_MAP = 1;
    private int currentFragment = FRAGMENT_USER_PRODUCTS;
    private MenuItem gpsMenuItem;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lobby);

        // Inicializar AuthService
        authService = AuthService.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerUserLobby, new UserProductFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        gpsMenuItem = menu.findItem(R.id.gps_options);
        return true;
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Aquí actualizamos el icono del menú dependiendo del fragmento que se esté mostrando
        if (currentFragment == FRAGMENT_MAP) {
            gpsMenuItem.setIcon(R.drawable.turn_back);// icono para el UserProductsFragment
        } else {
            gpsMenuItem.setIcon(android.R.drawable.ic_menu_mylocation); // icono para el MapFragment
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Manejar eventos de clic para cada elemento del menú
        if (id == R.id.nav_profile) {
            // Lógica para manejar el clic en el elemento "Perfil"
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_logout) {
            // Lógica para manejar el clic en el elemento "Cerrar sesión"
            logout();
            return true;
        }else if  (id==R.id.carrito){
            Intent intent=new Intent (this, OrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.gps_options) {
            toggleFragment();
            invalidateOptionsMenu();

        }

        return super.onOptionsItemSelected(item);
    }
    private void toggleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (currentFragment == FRAGMENT_USER_PRODUCTS) {
            fragmentTransaction.replace(R.id.containerUserLobby, new MapFragment());
            currentFragment = FRAGMENT_MAP;
        } else {
            // Asumiendo que tienes un fragmento llamado UserProductFragment
            fragmentTransaction.replace(R.id.containerUserLobby, new UserProductFragment());
            currentFragment = FRAGMENT_USER_PRODUCTS;
        }

        fragmentTransaction.commit();
    }

    /**
     * Método para manejar el evento de cierre de sesión.
     * Hace una llamada al AuthService para cerrar sesión del usuario actual.
     * Si tiene éxito, redirige al usuario a la pantalla de inicio de sesión.
     * Si hay un error, muestra un mensaje de error.
     */
    private void logout() {
        authService.logoutUser(new AuthService.AuthCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                // El logout fue exitoso, puedes redirigir al usuario a la pantalla de inicio de sesión u otra pantalla apropiada
                Intent intent = new Intent(ActivityUserLobby.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable t) {
                // Manejar cualquier error que ocurra durante el logout
                Toast.makeText(ActivityUserLobby.this, "Error al cerrar sesión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onAgricultoresInRange(List<User> agricultoresOnRange) {

    }
}
