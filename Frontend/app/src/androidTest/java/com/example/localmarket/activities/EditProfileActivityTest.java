package com.example.localmarket.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNull;

import android.os.Handler;
import android.os.Looper;

import org.hamcrest.Matcher;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.localmarket.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class EditProfileActivityTest {
    @Rule
    public ActivityScenarioRule<EditProfileActivity> activityScenarioRule =
            new ActivityScenarioRule<>(EditProfileActivity.class);

    @Test
    public void testOpenFragmentWithNullArgument() {
        ActivityScenario<EditProfileActivity> scenario = activityScenarioRule.getScenario();
        scenario.onActivity(activity -> {
            // Crear un fragmento de prueba vacío
            Fragment nullFragment = new Fragment();

            activity.openFragment(nullFragment);

            // Verificar que no se haya agregado ningún fragmento al contenedor
            assertNull(activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        });
    }


    @Test
    public void testDeleteAccountAndConfirmationDialog() {
        // Launch the activity
        ActivityScenario<EditProfileActivity> activityScenario = ActivityScenario.launch(EditProfileActivity.class);

        // Use onView() and other Espresso methods to interact with the UI
        activityScenario.onActivity(activity -> {
            // Perform click action to delete account
            onView(withId(R.id.deleteAccountButton)).perform(click());
        });

        // Espera hasta que aparezca el diálogo de confirmación
        onView(withText("¿Está seguro de que desea eliminar su cuenta? Esta acción no se puede deshacer."))
                .inRoot(isDialog()) // Asegura que se busque en la ventana de diálogo
                .check(matches(isDisplayed()));
    }

    @Test
    public void testShowLogoutConfirmationDialog() {
        // Launch the activity
        ActivityScenario<EditProfileActivity> activityScenario = ActivityScenario.launch(EditProfileActivity.class);

        // Use onView() and other Espresso methods to interact with the UI
        activityScenario.onActivity(activity -> {
            // Realiza la acción de cerrar sesión
            new Handler(Looper.getMainLooper()).post(() -> {
                // Realiza click en el menú de desbordamiento
                Espresso.openActionBarOverflowOrOptionsMenu(activity);
                // Realiza click en la opción de cerrar sesión
                Espresso.onView(withText("Cerrar sesión")).perform(click());
            });

            // Espera hasta que aparezca el diálogo de confirmación de cierre de sesión
            onView(withText("Cerrar sesión"))
                    .inRoot(isDialog()) // Asegura que se busque en la ventana de diálogo
                    .check(matches(isDisplayed()));
        });
    }




    // More test cases for other methods can be added similarly
}
