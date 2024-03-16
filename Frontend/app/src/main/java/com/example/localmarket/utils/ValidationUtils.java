package com.example.localmarket.utils;

public class ValidationUtils {
    public static class EmailValidator {

        public  boolean isValidEmail(String email) {
            if (email == null || email.isEmpty()) {
                return false;
            }

            // Verificar que haya un @ y al menos un punto después del @
            int atIndex = email.indexOf('@');
            if (atIndex == -1 || atIndex == 0 || atIndex == email.length() - 1) {
                return false;
            }

            int dotIndex = email.indexOf('.', atIndex);
            if (dotIndex == -1 || dotIndex == atIndex + 1 || dotIndex == email.length() - 1) {
                return false;
            }

            return true;
        }
    }

    public static class PasswordValidator {

        public boolean isValidPassword(String password) {
            // Verificar longitud mínima
            if (password == null || password.length() < 8) {
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
                return false;
            }

            return true;
        }
    }

    public static class NameValidator {

        public boolean isValidName(String name) {
            // Verificar si el nombre es nulo o está vacío
            if (name == null || name.isEmpty()) {
                return false;
            }

            // Verificar si el nombre contiene solo letras, espacios y letras con acento
            for (char c : name.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ' && !Character.isWhitespace(c)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static class UsernameValidator {

        public boolean isValidUsername(String username) {
            // Verificar si el nombre de usuario es nulo o está vacío
            if (username == null || username.isEmpty()) {
                return false;
            }

            // Verificar que el nombre de usuario no contenga caracteres especiales
            if (!username.matches("[a-zA-Z0-9]+")) {
                return false;
            }

            // TODO Aquí deberiamos implementar la lógica para verificar la unicidad del nombre de usuario en la base de datos


            // Por ahora, simplemente retornamos true
            return true;
        }
    }

}
