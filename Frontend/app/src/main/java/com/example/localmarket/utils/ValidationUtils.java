package com.example.localmarket.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.localmarket.R;

public class ValidationUtils {
    private static Context context;

    public static class EmailValidator {

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

    public static class PasswordValidator {

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

    public static class NameValidator {

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

    public static class UsernameValidator {

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

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
