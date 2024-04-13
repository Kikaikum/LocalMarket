package com.example.localmarket.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.localmarket.R;


/**
 * Clase de utilidad que contiene validadores para diferentes tipos de datos, como email, contraseña, nombre y nombre de usuario.
 * @author Ainoha + Oriol
 */
public class ValidationUtils {

    /**
     * Clase interna que contiene un validador para direcciones de correo electrónico.
     * @author Ainoha
     */
    public static class EmailValidator {

        /**
         * Valida una dirección de correo electrónico.
         * @param context Contexto de la aplicación.
         * @param email Dirección de correo electrónico a validar.
         * @return true si la dirección de correo electrónico es válida, false de lo contrario.
         */
        public static boolean isValidEmail(Context context, String email) {
            if (email == null || email.isEmpty()) {
                showToast(context, context.getString(R.string.toast_field_empty) + " email");
                return false;
            }

            // Verificar que haya un @ y al menos un punto después del @
            int atIndex = email.indexOf('@');
            if (atIndex == -1 || atIndex == 0 || atIndex == email.length() - 1) {
                showToast(context, context.getString(R.string.toast_invalid_email));
                return false;
            }

            int dotIndex = email.indexOf('.', atIndex);
            if (dotIndex == -1 || dotIndex == atIndex + 1 || dotIndex == email.length() - 1) {
                showToast(context, context.getString(R.string.toast_invalid_email));
                return false;
            }

            return true;
        }
    }

    /**
     * Clase interna que contiene un validador para contraseñas.
     * @author Oriol
     */
    public static class PasswordValidator {

        /**
         * Valida una contraseña.
         * @param context Contexto de la aplicación.
         * @param password Contraseña a validar.
         * @return true si la contraseña es válida, false de lo contrario.
         */
        public static boolean isValidPassword(Context context, String password) {
            // Verificar longitud mínima
            if (password == null || password.length() < 8) {
                showToast(context, context.getString(R.string.toast_field_min_8char));
                return false;
            }

            // Verificar al menos una letra mayúscula
            boolean hasUppercase = false;
            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    hasUppercase = true;
                    break;
                }
            }
            if (!hasUppercase) {
                showToast(context, context.getString(R.string.toast_field_not_uppercase));
                return false;
            }

            // Verificar al menos un carácter especial
            String specialCharacters = "!@#$%^&*()-+_";
            boolean hasSpecialCharacter = false;
            for (char c : password.toCharArray()) {
                if (specialCharacters.contains(String.valueOf(c))) {
                    hasSpecialCharacter = true;
                    break;
                }
            }
            if (!hasSpecialCharacter) {
                showToast(context, context.getString(R.string.toast_field_not_special_char));
                return false;
            }

            return true;
        }
    }

    /**
     * Clase interna que contiene un validador para nombres.
     * @author Ainoha
     */
    public static class NameValidator {

        /**
         * Valida un nombre.
         * @param context Contexto de la aplicación.
         * @param name Nombre a validar.
         * @return true si el nombre es válido, false de lo contrario.
         */
        public static boolean isValidName(Context context, String name) {
            // Verificar si el nombre es nulo o está vacío
            if (name == null || name.isEmpty()) {
                showToast(context, context.getString(R.string.toast_field_empty) + " nombre");
                return false;
            }

            // Verificar si el nombre contiene solo letras, espacios y letras con acento
            for (char c : name.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ' && !Character.isWhitespace(c)) {
                    showToast(context, context.getString(R.string.toast_invalid_special_characters));
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Clase interna que contiene un validador para nombres de usuario.
     * @author Oriol
     */
    public static class UsernameValidator {

        /**
         * Valida un nombre de usuario.
         * @param context Contexto de la aplicación.
         * @param username Nombre de usuario a validar.
         * @return true si el nombre de usuario es válido, false de lo contrario.
         */
        public static boolean isValidUsername(Context context, String username) {
            // Verificar si el nombre de usuario es nulo o está vacío
            if (username == null || username.isEmpty()) {
                showToast(context, context.getString(R.string.toast_field_empty) + " nombre de usuario");
                return false;
            }

            // Verificar que el nombre de usuario no contenga caracteres especiales
            if (!username.matches("[a-zA-Z0-9]+")) {
                showToast(context, context.getString(R.string.toast_invalid_special_characters));
                return false;
            }

            // TODO Aquí deberiamos implementar la lógica para verificar la unicidad del nombre de usuario en la base de datos

            // Por ahora, simplemente retornamos true
            return true;
        }
    }

    /**
     * Muestra un Toast en el hilo principal.
     * @param context Contexto de la aplicación.
     * @param message Mensaje del Toast.
     * @author Oriol
     */
    public static void showToast(Context context, String message) {
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        });
    }
}
