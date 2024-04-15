package com.example.localmarket.model;

/**
 * Clase que representa la respuesta de registro de un usuario.
 *
 * @author Oriol Estero Sanchez
 */
public class SignUpResponse {
    private boolean success;
    private String userId;
    private String errorMessage;

    /**
     * Método que indica si el registro fue exitoso.
     *
     * @return true si el registro fue exitoso, false de lo contrario.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Método para establecer si el registro fue exitoso.
     *
     * @param success true si el registro fue exitoso, false de lo contrario.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Método para obtener el ID de usuario.
     *
     * @return El ID de usuario.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Método para establecer el ID de usuario.
     *
     * @param userId El ID de usuario.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Método para obtener el mensaje de error, en caso de que haya ocurrido un error durante el registro.
     *
     * @return El mensaje de error.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Método para establecer el mensaje de error.
     *
     * @param errorMessage El mensaje de error.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
