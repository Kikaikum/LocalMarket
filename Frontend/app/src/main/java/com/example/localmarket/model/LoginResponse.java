package com.example.localmarket.model;

/**
 * Clase que representa la respuesta del servicio de inicio de sesión.
 * Contiene información sobre el éxito del inicio de sesión, el token de acceso, mensajes de error,
 * el rol del usuario (vendedor o comprador), el ID de usuario y los detalles del usuario.
 *
 * @author Oriol Estero Sanchez
 */
public class LoginResponse {
    private boolean success;
    private String token;
    private String errorMessage;
    private boolean vendor;
    private int userId;
    private User user;

    /**
     * Indica si el inicio de sesión fue exitoso.
     *
     * @return true si el inicio de sesión fue exitoso, de lo contrario false.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Establece si el inicio de sesión fue exitoso.
     *
     * @param success true si el inicio de sesión fue exitoso, de lo contrario false.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Obtiene el token de acceso generado durante el inicio de sesión.
     *
     * @return El token de acceso.
     */
    public String getToken() {
        return token;
    }

    /**
     * Establece el token de acceso.
     *
     * @param token El token de acceso a establecer.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Obtiene el mensaje de error en caso de un inicio de sesión fallido.
     *
     * @return El mensaje de error.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Establece el mensaje de error en caso de un inicio de sesión fallido.
     *
     * @param errorMessage El mensaje de error a establecer.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Indica si el usuario es un vendedor.
     *
     * @return true si el usuario es un vendedor, de lo contrario false.
     */
    public boolean isVendor() {
        return vendor;
    }

    /**
     * Establece si el usuario es un vendedor.
     *
     * @param vendor true si el usuario es un vendedor, de lo contrario false.
     */
    public void setVendor(boolean vendor) {
        this.vendor = vendor;
    }

    /**
     * Obtiene el ID de usuario.
     *
     * @return El ID de usuario.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Establece el ID de usuario.
     *
     * @param userId El ID de usuario a establecer.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Obtiene los detalles del usuario.
     *
     * @return Los detalles del usuario.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece los detalles del usuario.
     *
     * @param user Los detalles del usuario a establecer.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
