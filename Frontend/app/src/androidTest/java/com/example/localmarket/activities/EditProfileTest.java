package com.example.localmarket.activities;

import android.content.Context;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;

import com.example.localmarket.fragments.EditEmailFragment;
import com.example.localmarket.fragments.EditNameFragment;
import com.example.localmarket.fragments.EditPasswordFragment;
import com.example.localmarket.fragments.EditSurnameFragment;
import com.example.localmarket.fragments.EditUsernameFragment;
import com.example.localmarket.utils.ValidationUtils;
import androidx.fragment.app.testing.FragmentScenario;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Clase de prueba para verificar la validación de los campos de perfil editables.
 * Esta clase de prueba verifica la validación de los campos de correo electrónico, nombre,
 * contraseña, apellidos y nombre de usuario al editar el perfil de usuario.
 * @author Ainoha
 */
public class EditProfileTest {

    private static final String VALID_EMAIL = "test@example.com";
    private static final String INVALID_EMAIL = "invalidemail";

    private static final String VALID_NAME = "John Doe";
    private static final String INVALID_NAME = "12345";
    private static final String VALID_PASSWORD = "StrongPassword1!";
    private static final String INVALID_PASSWORD = "weakpass";
    private static final String VALID_SURNAME = "Doe";
    private static final String INVALID_SURNAME = "12345";
    private static final String VALID_USERNAME = "DoeUsername";
    private static final String INVALID_USERNAME = "Invalid-username";

    private EditEmailFragment editEmailFragment;
    private EditNameFragment editNameFragment;
    private EditPasswordFragment editPasswordFragment;
    private EditSurnameFragment editSurnameFragment;
    private EditUsernameFragment editUsernameFragment;
    private EditText editTextEmail;
    private EditText editTextName;
    private EditText editTextPassword;
    private EditText editTextSurname;
    private EditText editTextUsername;
    private ValidationUtils.EmailValidator emailValidator;
    private ValidationUtils.NameValidator nameValidator;
    private ValidationUtils.PasswordValidator passwordValidator;
    private ValidationUtils.NameValidator surnameValidator;
    private ValidationUtils.UsernameValidator usernameValidator;

    /**
     * Método de configuración que se ejecuta antes de cada prueba.
     * Aquí se inicializan los fragmentos, campos de texto y validadores necesarios para las pruebas.
     */
    @Before
    public void setUp() {
        // Obtiene el contexto de la aplicación
        Context context = ApplicationProvider.getApplicationContext();

        // Inicializa los fragmentos y los EditText con el contexto de la aplicación
        editEmailFragment = new EditEmailFragment();
        editNameFragment = new EditNameFragment();
        editPasswordFragment = new EditPasswordFragment();
        editSurnameFragment = new EditSurnameFragment();
        editUsernameFragment=new EditUsernameFragment();

        editTextEmail = new EditText(context);
        editTextName = new EditText(context);
        editTextPassword = new EditText(context);
        editTextSurname = new EditText(context);
        editTextUsername=new EditText(context);


        emailValidator = new ValidationUtils.EmailValidator();
        nameValidator = new ValidationUtils.NameValidator();
        passwordValidator = new ValidationUtils.PasswordValidator();
        surnameValidator = new ValidationUtils.NameValidator();
        usernameValidator=new ValidationUtils.UsernameValidator();
    }
    /**
     * Método de prueba para validar un correo electrónico válido.
     * Se espera que el método isValidEmail() devuelva true para un correo electrónico válido.
     */
    @Test
    public void testEmailValidation_ValidEmail() {
        // Simula la entrada del usuario
        editTextEmail.setText(VALID_EMAIL);

        // Llama al método bajo prueba
        boolean isValid = emailValidator.isValidEmail(editEmailFragment.getContext(), editTextEmail.getText().toString());

        // Verifica que el correo electrónico se valide correctamente
        assertTrue(isValid);
    }
    /**
     * Método de prueba para validar un correo electrónico inválido.
     * Se espera que el método isValidEmail() devuelva false para un correo electrónico inválido.
     */
    @Test
    public void testEmailValidation_InvalidEmail() {
        // Obtiene el contexto de la aplicación
        Context appContext = ApplicationProvider.getApplicationContext();

        // Simula la entrada del usuario
        editTextEmail.setText(INVALID_EMAIL);

        // Llama al método bajo prueba pasando el contexto de la aplicación
        boolean isValid = emailValidator.isValidEmail(appContext, editTextEmail.getText().toString());

        // Verifica que el correo electrónico no se valide
        assertFalse(isValid);
    }
    /**
     * Método de prueba para validar un nombre válido.
     * Se espera que el método isValidName() devuelva true para un correo electrónico válido.
     */
    @Test
    public void testNameValidation_ValidName() {
        // Simula la entrada del usuario
        editTextName.setText(VALID_NAME);

        // Llama al método bajo prueba
        boolean isValid = nameValidator.isValidName(editNameFragment.getContext(), editTextName.getText().toString());

        // Verifica que el nombre se valide correctamente
        assertTrue(isValid);
    }
    /**
     * Método de prueba para validar un nombre inválido.
     * Se espera que el método isValidNamel() devuelva false para un nombre inválido.
     */
    @Test
    public void testNameValidation_InvalidName() {
        // Obtiene el contexto de la aplicación
        Context appContext = ApplicationProvider.getApplicationContext();

        // Simula la entrada del usuario
        editTextName.setText(INVALID_NAME);

        // Llama al método bajo prueba pasando el contexto de la aplicación
        boolean isValid = nameValidator.isValidName(appContext, editTextName.getText().toString());

        // Verifica que el nombre no se valide
        assertFalse(isValid);
    }
    /**
     * Método de prueba para validar un password válido.
     * Se espera que el método isValidPassword() devuelva true para un password válido.
     */
    @Test
    public void testPasswordValidation_ValidPassword() {
        // Simula la entrada del usuario
        editTextPassword.setText(VALID_PASSWORD);

        // Llama al método bajo prueba
        boolean isValid = passwordValidator.isValidPassword(editPasswordFragment.getContext(), editTextPassword.getText().toString());

        // Verifica que la contraseña se valide correctamente
        assertTrue(isValid);
    }
    /**
     * Método de prueba para validar un password inválido.
     * Se espera que el método isValidPassword() devuelva false para un password inválido.
     */
    @Test
    public void testPasswordValidation_InvalidPassword() {


        // Simula la creación del fragmento
        FragmentScenario<EditPasswordFragment> fragmentScenario = FragmentScenario.launchInContainer(EditPasswordFragment.class);
        fragmentScenario.onFragment(fragment -> {
            // Configura el contexto para el fragmento
            editPasswordFragment = fragment;
        });
        // Simula la entrada del usuario
        editTextPassword.setText(INVALID_PASSWORD);

        // Llama al método bajo prueba
        boolean isValid = passwordValidator.isValidPassword(editPasswordFragment.getContext(), editTextPassword.getText().toString());

        // Verifica que la contraseña no se valide
        assertFalse(isValid);
    }

    /**
     * Método de prueba para validar apellidos válidos.
     * Se espera que el método isValidName() devuelva true para un apellido válido.
     */
    @Test
    public void testSurnameValidation_ValidSurname() {
        // Simula la entrada del usuario
        editTextSurname.setText(VALID_SURNAME);

        // Llama al método bajo prueba
        boolean isValid = surnameValidator.isValidName(editSurnameFragment.getContext(), editTextSurname.getText().toString());

        // Verifica que los apellidos se validen correctamente
        assertTrue(isValid);
    }
    /**
     * Método de prueba para validar apellidos inválidos.
     * Se espera que el método isValidName() devuelva false para un apellido inválido.
     */
    @Test
    public void testSurnameValidation_InvalidSurname() {
        // Obtiene el contexto de la aplicación
        Context appContext = ApplicationProvider.getApplicationContext();

        // Simula la entrada del usuario
        editTextSurname.setText(INVALID_SURNAME);

        // Llama al método bajo prueba pasando el contexto de la aplicación
        boolean isValid = nameValidator.isValidName(appContext, editTextSurname.getText().toString());

        // Verifica que el nombre no se valide
        assertFalse(isValid);
    }
    /**
     * Método de prueba para validar nombre de usuario válido.
     * Se espera que el método isValidUsername() devuelva true para un nombre de usuario válido.
     */
    @Test
    public void testUsernameValidation_ValidUsername() {
        // Simula la entrada del usuario
        editTextUsername.setText(VALID_USERNAME);

        // Llama al método bajo prueba
        boolean isValid = usernameValidator.isValidUsername(editUsernameFragment.getContext(), editTextUsername.getText().toString());

        // Verifica que los apellidos se validen correctamente
        assertTrue(isValid);
    }
    /**
     * Método de prueba para validar nombre de usuario inválido.
     * Se espera que el método isValidUsername() devuelva false para un nombre de usuario inválido.
     */
    @Test
    public void testUserameValidation_InvalidUsername() {
        // Obtiene el contexto de la aplicación
        Context appContext = ApplicationProvider.getApplicationContext();

        // Simula la entrada del usuario
            editTextUsername.setText(INVALID_USERNAME);

        // Llama al método bajo prueba pasando el contexto de la aplicación
        boolean isValid = nameValidator.isValidName(appContext, editTextUsername.getText().toString());

        // Verifica que el nombre no se valide
        assertFalse(isValid);
    }
}
