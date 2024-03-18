package com.example.localmarket;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class EditProfileActivityTest {

    @Rule
    public ActivityScenarioRule<EditProfileActivity> activityRule =
            new ActivityScenarioRule<>(EditProfileActivity.class);

    @Test
    public void testEditProfileActivity() {
        // Verificar que los elementos de la interfaz de usuario estén presentes
        onView(withId(R.id.editUsernameButton)).check(matches(isDisplayed()));
        onView(withId(R.id.editEmailButton)).check(matches(isDisplayed()));
        onView(withId(R.id.editPasswordButton)).check(matches(isDisplayed()));
        onView(withId(R.id.deleteAccountButton)).check(matches(isDisplayed()));


        // Simular la eliminación de la cuenta
        onView(withId(R.id.deleteAccountButton)).perform(click());
        // Verificar que se muestre el diálogo de confirmación
        onView(withText("Eliminar cuenta")).check(matches(isDisplayed()));
        // Simular confirmar la eliminación de la cuenta
        onView(withText("Eliminar")).perform(click());
        // Verificar que se muestre el mensaje de éxito
        onView(withText("La cuenta se ha eliminado correctamente.")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
